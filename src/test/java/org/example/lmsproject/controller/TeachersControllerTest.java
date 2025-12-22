package org.example.lmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.AbstractIt;
import org.example.lmsproject.dao.TeachersRepository;
import org.example.lmsproject.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RequiredArgsConstructor
public class TeachersControllerTest extends AbstractIt {


    @Autowired
    private TeachersRepository teachersRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        Teacher teacher1 = new Teacher(null,"Иван","Петров", null);
        Teacher teacher2 = new Teacher(null,"Мария","Сидорова", null);
        Teacher teacher3 = new Teacher(null,"Алексей","Иванов", null);

        teachersRepository.save(teacher1);
        teachersRepository.save(teacher2);
        teachersRepository.save(teacher3);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        teachersRepository.deleteAll();
    }

    @Test
    void getAllTeachers() {
        Teacher[] teachers = restClient.get()
                .uri("/api/v1/teachers")
                .retrieve()
                .body(Teacher[].class);

        assertNotNull(teachers);
        assertEquals(3, teachers.length);

        assertEquals("Иван", teachers[0].getName());
        assertEquals("Петров",  teachers[0].getSurname());

        assertEquals("Мария", teachers[1].getName());
        assertEquals("Сидорова",  teachers[1].getSurname());

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
        assertEquals("Иван", teachers[0].getName());
        assertEquals("Петров", teachers[0].getSurname());
    }
}
