package kz.oj.tinkoffhw5.integrationtests;

import kz.oj.tinkoffhw5.entity.Event;
import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.repository.EventRepository;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class EventIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

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
                .andExpect(jsonPath("$[0]."))
    }


    private Place createPlace() {

        Place place = new Place();
        place.setName("Алматы");
        place.setSlug("02");
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
