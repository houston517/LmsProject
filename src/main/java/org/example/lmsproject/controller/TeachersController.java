package org.example.lmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.TeacherInDto;
import org.example.lmsproject.dto.TeacherOutDto;
import org.example.lmsproject.service.TeachersService;
import org.example.lmsproject.util.LmsProjectUtil;
import org.springframework.data.domain.Pageable;
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
    public List<TeacherOutDto> getAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        Pageable pageable = LmsProjectUtil.getPageable(pageSize, pageNumber);
        return teachersService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody TeacherInDto teacherInDto) {
        teachersService.save(teacherInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity update(@Valid @RequestBody TeacherInDto teacherInDto) {
        teachersService.update(teacherInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody TeacherInDto teacherInDto) {
        teachersService.delete(teacherInDto);
        return ResponseEntity.ok().build();
    }
}
