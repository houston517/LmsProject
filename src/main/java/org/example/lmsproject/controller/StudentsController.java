package org.example.lmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.StudentInDto;
import org.example.lmsproject.dto.StudentOutDto;
import org.example.lmsproject.service.StudentsService;
import org.example.lmsproject.util.LmsProjectUtil;
import org.springframework.data.domain.Pageable;
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
    public List<StudentOutDto> getAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        Pageable pageable = LmsProjectUtil.getPageable(pageSize, pageNumber);
        return studentsService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody StudentInDto studentInDto) {
        studentsService.save(studentInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity update(@Valid @RequestBody StudentInDto studentInDto) {
        studentsService.update(studentInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody StudentInDto studentInDto) {
        studentsService.delete(studentInDto);
        return ResponseEntity.ok().build();
    }

}
