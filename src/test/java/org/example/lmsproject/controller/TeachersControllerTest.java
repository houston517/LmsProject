package org.example.lmsproject.controller;

import org.example.lmsproject.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class TeachersControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.change-log",
                () -> "classpath:db/changelog/db.changelog-test-master.yaml");
    }

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        this.restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    void getAllTeachers() {
        Teacher[] teachers = restClient.get()
                .uri("/api/v1/teachers")
                .retrieve()
                .body(Teacher[].class);

        assertNotNull(teachers);
        assertEquals(3, teachers.length);

        assertEquals(1, teachers[0].getId());
        assertEquals("Иван", teachers[0].getName());
        assertEquals("Петров",  teachers[0].getSurname());

        assertEquals(2, teachers[1].getId());
        assertEquals("Мария", teachers[1].getName());
        assertEquals("Сидорова",  teachers[1].getSurname());

        assertEquals(3, teachers[2].getId());
        assertEquals("Алексей", teachers[2].getName());
        assertEquals("Иванов",  teachers[2].getSurname());

        teachers = restClient.get().uri(uriBuilder ->
                uriBuilder
                .path("/api/v1/teachers")
                .queryParam("size",1)
                .queryParam("page",1)
                .queryParam("sort","name,asc")
                .build())
            .retrieve().body(Teacher[].class);
        assertNotNull(teachers);
        assertEquals(1, teachers.length);
        assertEquals(1, teachers[0].getId());
    }
}
