package kz.oj.tinkoffhw5.integrationtests;

import kz.oj.tinkoffhw5.entity.Location;
import kz.oj.tinkoffhw5.repository.LocationRepository;
import kz.oj.tinkoffhw5.service.LocationInitializationService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@Testcontainers
@AutoConfigureWireMock(port = 8081)
@ActiveProfiles("test")
public class LocationInitializationServiceIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Test
    void connectionEstablished() {
        postgres.isCreated();
        postgres.isRunning();
    }

    @Autowired
    private LocationInitializationService locationInitializationService;

    @Autowired
    private LocationRepository locationRepository;

    @AfterEach
    void tearDown() {

        locationRepository.deleteAll();
    }

    @Test
    void testRun() {

        // Given
        String expectedBody = """
                    [
                        {
                            "slug": "ala",
                            "name": "Алматы"
                        },
                        {
                            "slug": "nqz",
                            "name": "Астана"
                        }
                    ]
                """;

        stubFor(
                get(urlEqualTo("/locations"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                                .withBody(expectedBody)
                        )
        );

        // When
        locationInitializationService.initializeLocations();

        // Then
        List<Location> locations = locationRepository.findAll();

        assertNotNull(locations);
        assertEquals(2, locations.size());

        Set<String> names = locations.stream().map(Location::getName).collect(Collectors.toSet());
        assertTrue(names.containsAll(List.of("Алматы", "Астана")));

        Set<String> slugs = locations.stream().map(Location::getSlug).collect(Collectors.toSet());
        assertTrue(slugs.containsAll(List.of("ala", "nqz")));
    }
}
