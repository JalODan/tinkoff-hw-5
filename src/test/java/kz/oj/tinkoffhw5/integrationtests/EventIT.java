package kz.oj.tinkoffhw5.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kz.oj.tinkoffhw5.entity.Event;
import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.repository.EventRepository;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.web.rest.v1.dto.EventDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class EventIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    public EventIT() {

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void connectionEstablished() {
        postgres.isCreated();
        postgres.isRunning();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventRepository eventRepository;

    private final ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        placeRepository.deleteAll();
    }

    private String baseUrl = "/api/v1/events";

    @Test
    void findAll() throws Exception {

        Place place = createPlace();
        createEvent("Вечер песен композитора Шамши Калдаякова", place, LocalDate.of(2024, 11, 07));
        createEvent("Спектакль 'Он сам нарвался' в Алматы", place, LocalDate.of(2024, 11, 14));

        mockMvc.perform(get(baseUrl)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Вечер песен композитора Шамши Калдаякова"))
                .andExpect(jsonPath("$[0].date").value("2024-11-07"))
                .andExpect(jsonPath("$[0].place.id").value(place.getId().toString()))
                .andExpect(jsonPath("$[0].place.slug").value("02"))
                .andExpect(jsonPath("$[0].place.name").value("Алматы"))
                .andExpect(jsonPath("$[1].name").value("Спектакль 'Он сам нарвался' в Алматы"))
                .andExpect(jsonPath("$[1].date").value("2024-11-14"))
                .andExpect(jsonPath("$[1].place.id").value(place.getId().toString()))
                .andExpect(jsonPath("$[0].place.slug").value("02"))
                .andExpect(jsonPath("$[0].place.name").value("Алматы"));
    }

    @Test
    void givenNonexistentId_whenFindById_thenNotFound() throws Exception {

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ENTITY_NOT_FOUND"));
    }

    @Test
    void findById() throws Exception {

        Place place = createPlace();
        Event event = createEvent("Вечер песен композитора Шамши Калдаякова", place, LocalDate.of(2024, 11, 07));

        mockMvc.perform(get(baseUrl + "/" + event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Вечер песен композитора Шамши Калдаякова"))
                .andExpect(jsonPath("$.date").value("2024-11-07"))
                .andExpect(jsonPath("$.place.id").value(place.getId().toString()))
                .andExpect(jsonPath("$.place.slug").value("02"))
                .andExpect(jsonPath("$.place.name").value("Алматы"));
    }

    @Test
    void create() throws Exception {

        Place place = createPlace();
        String json = new JSONObject()
                .put("name", "День республики")
                .put("date", "2024-10-25")
                .put("placeId", place.getId())
                .toString();

        MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        EventDto eventDto = objectMapper.readValue(responseBody, EventDto.class);

        Optional<Event> eventOptional = eventRepository.findById(eventDto.getId());
        assertTrue(eventOptional.isPresent());

        Event event = eventOptional.get();
        assertEquals("День республики", event.getName());
        assertEquals(LocalDate.of(2024, 10, 25), event.getDate());
        assertEquals(place.getId(), event.getPlace().getId());
    }

    @Test
    void update() throws Exception {

        // Given
        Place place = createPlace();
        Event event = createEvent("День республики", place, LocalDate.of(2024, 10, 25));

        String newName = "Nationalfeiertag";
        Place newPlace = createPlace("Вена", "wien");
        LocalDate newDate = LocalDate.of(2024, 10, 24);

        String json = new JSONObject()
                .put("name", newName)
                .put("date", newDate)
                .put("placeId", newPlace.getId())
                .toString();

        // When
        mockMvc.perform(patch(baseUrl + "/" + event.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
        ).andExpect(status().isOk());

        // Then
        Optional<Event> eventOptional = eventRepository.findById(event.getId());
        assertTrue(eventOptional.isPresent());

        Event updatedEvent = eventOptional.get();
        assertEquals(newName, updatedEvent.getName());
        assertEquals(newDate, updatedEvent.getDate());
        assertEquals(newPlace.getId(), updatedEvent.getPlace().getId());
    }

    @Test
    void testDelete() throws Exception {

        Place place = createPlace("Шымкент", "17");
        Event event = createEvent("Тетин юбилей", place, LocalDate.of(2024, 11, 11));

        mockMvc.perform(delete(baseUrl + "/" + event.getId()))
                .andExpect(status().isOk());

        assertTrue(placeRepository.existsById(place.getId()));
        assertFalse(eventRepository.existsById(event.getId()));
    }


    private Place createPlace() {

        return createPlace("Алматы", "02");
    }

    private Place createPlace(String name, String slug) {

        Place place = new Place();
        place.setName(name);
        place.setSlug(slug);
        return placeRepository.save(place);
    }

    private Event createEvent(String name, Place place, LocalDate date) {

        Event event = new Event();
        event.setName(name);
        event.setPlace(place);
        event.setDate(date);

        return eventRepository.save(event);
    }
}
