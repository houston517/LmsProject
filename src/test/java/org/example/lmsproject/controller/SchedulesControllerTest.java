package org.example.lmsproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.AbstractIt;
import org.example.lmsproject.dao.CoursesRepository;
import org.example.lmsproject.dao.GroupsRepository;
import org.example.lmsproject.dao.SchedulesRepository;
import org.example.lmsproject.dao.TeachersRepository;
import org.example.lmsproject.dto.ScheduleInDto;
import org.example.lmsproject.dto.ScheduleOutDto;
import org.example.lmsproject.mapper.ScheduleMapper;
import org.example.lmsproject.model.Course;
import org.example.lmsproject.model.Group;
import org.example.lmsproject.model.Schedule;
import org.example.lmsproject.model.Teacher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class SchedulesControllerTest extends AbstractIt {

    public static final String URI = "api/v1/schedules";
    @Autowired
    private SchedulesRepository schedulesRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private CoursesRepository cursesRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    private final List<ScheduleOutDto> scheduleDtoList = new ArrayList<>();
    @BeforeEach
    void setUp(){
        Group group1 = new Group();
        group1.setName("Group 1");

        Group  group2 = new Group();
        group2.setName("Group 2");

        groupsRepository.save(group1);
        groupsRepository.save(group2);

        Teacher teacher1 = new Teacher(null,"Иван","Петров", null);
        Teacher teacher2 = new Teacher(null,"Мария","Сидорова", null);

        teachersRepository.save(teacher1);
        teachersRepository.save(teacher2);

        Course course1 = new Course();
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setName("Course 2");

        cursesRepository.save(course1);
        cursesRepository.save(course2);

        Schedule schedule1 = new Schedule();
        schedule1.setGroup(group1);
        schedule1.setCourse(course1);
        schedule1.setTeacher(teacher1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse("26.12.2025 10:00:00",dateFormat);
        schedule1.setLessonDate(localDateTime1);

        Schedule schedule2 = new Schedule();
        schedule2.setGroup(group2);
        schedule2.setCourse(course2);
        schedule2.setTeacher(teacher2);
        LocalDateTime localDateTime2 = LocalDateTime.parse("26.12.2025 12:00:00",dateFormat);
        schedule2.setLessonDate(localDateTime2);

        schedulesRepository.save(schedule1);
        schedulesRepository.save(schedule2);

        scheduleDtoList.add(scheduleMapper.toDto(schedule1));
        scheduleDtoList.add(scheduleMapper.toDto(schedule2));
    }

    @AfterEach
    @Transactional
    void tearDown(){
        schedulesRepository.deleteAll();
        groupsRepository.deleteAll();
        teachersRepository.deleteAll();
        cursesRepository.deleteAll();
    }

    @Test
    void getAllSchedules(){
        ScheduleOutDto[] schedules = restClient.get()
                .uri(URI)
                .retrieve()
                .body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(2, schedules.length);

        checkFields(schedules[0],0);
        checkFields(schedules[1],1);

        schedules = restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path(URI)
                                .queryParam("size",1)
                                .queryParam("page",0)
                                .queryParam("sort","group,desc")
                                .build())
                .retrieve().body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(1, schedules.length);

        checkFields(schedules[0],1);

        schedules = restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path(URI)
                                .queryParam("groupId",1)
                                .queryParam("teacherId",1)
                                .build())
                .retrieve().body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(1, schedules.length);

        checkFields(schedules[0],0);
    }

    void checkFields(ScheduleOutDto schedule, int index){
        ScheduleOutDto expected = scheduleDtoList.get(index);
        assertEquals(expected.getGroup(),  schedule.getGroup());
        assertEquals(expected.getCourse(),  schedule.getCourse());
        assertEquals(expected.getTeacher(), schedule.getTeacher());

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime expectedDateTime = LocalDateTime.parse(expected.getLessonDate(),dateFormat);
        assertEquals(expectedDateTime, LocalDateTime.parse(schedule.getLessonDate(),dateFormat));
    }

    @Test
    void createSchedule(){
        Group  group3 = new Group();
        group3.setName("Group 3");
        groupsRepository.save(group3);

        String lessonDate = "27.12.2025 13:00:00";

        ScheduleInDto scheduleInDto = new ScheduleInDto();
        scheduleInDto.setGroupId(group3.getId());
        List<Teacher> teachers = teachersRepository.findAll();
        scheduleInDto.setTeacherId(teachers.getFirst().getId());
        List<Course> courses = cursesRepository.findAll();
        scheduleInDto.setCourseId(courses.getFirst().getId());
        scheduleInDto.setLessonDate(lessonDate);

        ScheduleOutDto scheduleOutDto = new ScheduleOutDto();
        scheduleOutDto.setLessonDate(lessonDate);
        scheduleOutDto.setCourse("Course 1");
        scheduleOutDto.setTeacher("Петров Иван");
        scheduleOutDto.setGroup("Group 3");
        scheduleDtoList.add(scheduleOutDto);

        ResponseEntity<?> response = restClient.post().uri(URI)
                .body(scheduleInDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        getErrorHandler()).toBodilessEntity();
        assertTrue(response.getStatusCode().is2xxSuccessful());

        ScheduleOutDto[] schedules = restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path(URI)
                                .queryParam("groupId",group3.getId())
                                .build())
                .retrieve().body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(1, schedules.length);
        checkFields(schedules[0],2);

        scheduleInDto.setGroupId(null);
        response = restClient.post().uri(URI)
                .body(scheduleInDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {}).toBodilessEntity();

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void updateSchedule(){
        Group  group3 = new Group();
        group3.setName("Group 3");
        groupsRepository.save(group3);

        Teacher teacher3 = new Teacher(null,"Алексей","Иванов", null);
        teachersRepository.save(teacher3);

        Course course3 = new Course();
        course3.setName("Course 3");
        cursesRepository.save(course3);

        String lessonDate = "27.12.2025 13:00:00";

        ScheduleInDto scheduleInDto = new ScheduleInDto();
        scheduleInDto.setGroupId(group3.getId());
        scheduleInDto.setTeacherId(teacher3.getId());
        scheduleInDto.setCourseId(course3.getId());
        scheduleInDto.setLessonDate(lessonDate);

        ScheduleOutDto scheduleOutDto = new ScheduleOutDto();
        scheduleOutDto.setLessonDate(lessonDate);
        scheduleOutDto.setCourse("Course 3");
        scheduleOutDto.setTeacher("Иванов Алексей");
        scheduleOutDto.setGroup("Group 3");
        scheduleDtoList.add(scheduleOutDto);

        ScheduleOutDto[] schedules = restClient.get()
                .uri(URI)
                .retrieve()
                .body(ScheduleOutDto[].class);
        assertNotNull(schedules);

        Long id = schedules[0].getId();
        scheduleInDto.setId(id);

        ResponseEntity<?> response = restClient.post().uri(URI)
                .body(scheduleInDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        getErrorHandler()).toBodilessEntity();
        assertTrue(response.getStatusCode().is2xxSuccessful());

        schedules = restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path(URI)
                                .queryParam("groupId",group3.getId())
                                .queryParam("teacherId",teacher3.getId())
                                .build())
                .retrieve().body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(1, schedules.length);

        checkFields(schedules[0],scheduleDtoList.size()-1);

    }

    private static RestClient.ResponseSpec.@NotNull ErrorHandler getErrorHandler() {
        return (request, responseEntity) -> {
            String errorBody = StreamUtils.copyToString(
                    responseEntity.getBody(),
                    StandardCharsets.UTF_8
            );
            fail(errorBody);
        };
    }

    @Test
    void deleteSchedule(){
        ScheduleInDto scheduleInDto = new ScheduleInDto();

        ScheduleOutDto[] schedules = restClient.get()
                .uri(URI)
                .retrieve()
                .body(ScheduleOutDto[].class);
        assertNotNull(schedules);

        ScheduleOutDto scheduleOutDto = schedules[0];

        scheduleInDto.setId(schedules[0].getId());

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(scheduleInDto);
        } catch (JsonProcessingException e){
            fail(e.getMessage());
        }

        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> body = new HttpEntity<>(json, headers);
        String fullUri = "http://localhost:" + getPort() + "/" + URI;
        ResponseEntity<String> response = client.exchange(
                fullUri, HttpMethod.DELETE, body, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        schedules = restClient.get()
                .uri(URI)
                .retrieve()
                .body(ScheduleOutDto[].class);
        assertNotNull(schedules);
        assertEquals(1, schedules.length);
        assertNotEquals(scheduleOutDto.getCourse(), schedules[0].getCourse());
        assertNotEquals(scheduleOutDto.getTeacher(), schedules[0].getTeacher());
        assertNotEquals(scheduleOutDto.getGroup(), schedules[0].getGroup());
        assertNotEquals(scheduleOutDto.getLessonDate(), schedules[0].getLessonDate());
    }
}
