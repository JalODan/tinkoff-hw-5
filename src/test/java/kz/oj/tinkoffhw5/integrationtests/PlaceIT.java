package kz.oj.tinkoffhw5.integrationtests;

import kz.oj.tinkoffhw5.entity.Place;
import kz.oj.tinkoffhw5.repository.PlaceRepository;
import kz.oj.tinkoffhw5.web.rest.v1.dto.PlaceDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PlaceIT {

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        placeRepository.deleteAll();
    }

    private final String baseUrl = "/api/v1/places";

    @Test
    void findAll() throws Exception {

        // Given
        createPlace("Астана", "01");
        createPlace("Алматы", "02");

        // When
        ResultActions perform = mockMvc.perform(get(baseUrl));

        // Then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Астана"))
                .andExpect(jsonPath("$[0].slug").value("01"))
                .andExpect(jsonPath("$[1].name").value("Алматы"))
                .andExpect(jsonPath("$[1].slug").value("02"));
    }

    @Test
    void givenNonexistentId_whenFindById_thenNotFound() throws Exception {

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void findById() throws Exception {

        Place place = createPlace("Алматы", "01");

        mockMvc.perform(get(baseUrl + "/" + place.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Алматы"))
                .andExpect(jsonPath("$.slug").value("01"));
    }

    @Test
    void create() throws Exception {

        String json = """
                {
                    "slug": "02",
                    "name": "Алматы"
                }
                """;

        MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        PlaceDto placeDto = objectMapper.readValue(responseBody, PlaceDto.class);

        Optional<Place> placeOptional = placeRepository.findById(placeDto.getId());
        assertTrue(placeOptional.isPresent());

        Place place = placeOptional.get();
        assertEquals("02", place.getSlug());
        assertEquals("Алматы", place.getName());
    }

    private Place createPlace(String name, String slug) {

        Place place = new Place();
        place.setName(name);
        place.setSlug(slug);
        return placeRepository.save(place);
    }

}
