package org.example.LMSProject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dto.StudentInDto;
import org.example.LMSProject.dto.StudentOutDto;
import org.example.LMSProject.service.StudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentsController {

    private final StudentsService studentsService;

    @GetMapping()
    public List<StudentOutDto> getAllStudents() {
        return studentsService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity createStudent(@Valid @RequestBody StudentInDto studentInDto) {
        studentsService.save(studentInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity updateStudent(@Valid @RequestBody StudentInDto studentInDto) {
        studentsService.update(studentInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteStudent(@RequestBody StudentInDto studentInDto) {
        studentsService.delete(studentInDto);
        return ResponseEntity.ok().build();
    }

}
