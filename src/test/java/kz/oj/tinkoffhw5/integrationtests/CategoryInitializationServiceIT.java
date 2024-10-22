package kz.oj.tinkoffhw5.integrationtests;


import kz.oj.tinkoffhw5.entity.Category;
import kz.oj.tinkoffhw5.repository.CategoryRepository;
import kz.oj.tinkoffhw5.service.CategoryInitializationService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public class CategoryInitializationServiceIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Test
    void connectionEstablished() {
        postgres.isCreated();
        postgres.isRunning();
    }

    @Autowired
    private CategoryInitializationService categoryInitializationService;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void testRun() {

        // Given
        String expectedBody = """
                        [
                            {
                                "id": 123,
                                "slug": "airports",
                                "name": "Аэропорты"
                            },
                            {
                                "id": 89,
                                "slug": "amusement",
                                "name": "Развлечения"
                            }
                        ]
                """;

        stubFor(
                get(urlEqualTo("/categories"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                                .withBody(expectedBody)
                        )
        );

        // When
        categoryInitializationService.initializeCategories();

        // Then
        List<Category> categories = categoryRepository.findAll();

        assertNotNull(categories);
        assertEquals(2, categories.size());

        assertTrue(categories.stream().map(Category::getName).collect(Collectors.toSet()).containsAll(List.of("Аэропорты", "Развлечения")));
        assertTrue(categories.stream().map(Category::getSlug).collect(Collectors.toSet()).containsAll(List.of("airports", "amusement")));
    }
}
