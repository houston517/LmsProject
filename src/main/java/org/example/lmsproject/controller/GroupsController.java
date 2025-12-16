package org.example.lmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.GroupInDto;
import org.example.lmsproject.dto.GroupOutDto;
import org.example.lmsproject.service.GroupsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/groups")
public class GroupsController {
    private final GroupsService groupsService;

    @GetMapping()
    public List<GroupOutDto> getAll(Pageable pageable) {
        return groupsService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public GroupOutDto getGroupById(@PathVariable("id") Long id) {
        return groupsService.getGroupById(id);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody GroupInDto groupInDto) {
        groupsService.save(groupInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity update(@Valid @RequestBody GroupInDto groupInDto) {
        groupsService.update(groupInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/addStudents")
    public ResponseEntity addStudents(@RequestBody GroupInDto groupInDto) {
        groupsService.addStudents(groupInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody GroupInDto groupInDto) {
        groupsService.delete(groupInDto);
        return ResponseEntity.ok().build();
    }
}
