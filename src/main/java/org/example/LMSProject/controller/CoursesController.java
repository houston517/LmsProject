package org.example.LMSProject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dto.CourseInDto;
import org.example.LMSProject.dto.CourseOutDto;
import org.example.LMSProject.service.CoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/courses")
public class CoursesController {
    private final CoursesService coursesService;
    @GetMapping()
    public List<CourseOutDto> getAllCourses() {
        return coursesService.getAllCourses();
    }

    @PostMapping
    public ResponseEntity createCourse(@Valid @RequestBody CourseInDto courseInDto) {
        coursesService.save(courseInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity updateCourse(@Valid @RequestBody CourseInDto courseInDto) {
        coursesService.update(courseInDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity deleteCourse(@RequestBody CourseInDto courseInDto) {
        coursesService.delete(courseInDto);
        return ResponseEntity.ok().build();
    }
}
