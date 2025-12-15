package org.example.LMSProject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dto.TeacherInDto;
import org.example.LMSProject.dto.TeacherOutDto;
import org.example.LMSProject.service.TeachersService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/teachers")
public class TeachersController {
    private final TeachersService teachersService;

    @GetMapping()
    public List<TeacherOutDto> getAllTeachers() {
        return teachersService.getAllTeachers();
    }

    @PostMapping
    public ResponseEntity createTeacher(@Valid @RequestBody TeacherInDto teacherInDto) {
        teachersService.save(teacherInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity updateTeacher(@Valid @RequestBody TeacherInDto teacherInDto) {
        teachersService.update(teacherInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteTeacher(@RequestBody TeacherInDto teacherInDto) {
        teachersService.delete(teacherInDto);
        return ResponseEntity.ok().build();
    }
}
