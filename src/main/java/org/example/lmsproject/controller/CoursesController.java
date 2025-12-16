package org.example.lmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.CourseInDto;
import org.example.lmsproject.dto.CourseOutDto;
import org.example.lmsproject.service.CoursesService;
import org.example.lmsproject.util.LmsProjectUtil;
import org.springframework.data.domain.Pageable;
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
    public List<CourseOutDto> getAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        Pageable pageable = LmsProjectUtil.getPageable(pageSize, pageNumber);
        return coursesService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CourseInDto courseInDto) {
        coursesService.save(courseInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity update(@Valid @RequestBody CourseInDto courseInDto) {
        coursesService.update(courseInDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    public ResponseEntity delete(@RequestBody CourseInDto courseInDto) {
        coursesService.delete(courseInDto);
        return ResponseEntity.ok().build();
    }
}
