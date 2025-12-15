package org.example.LMSProject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dto.GroupInDto;
import org.example.LMSProject.dto.GroupOutDto;
import org.example.LMSProject.service.GroupsService;
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
    public List<GroupOutDto> getAllGroups() {
        return groupsService.getAllGroups();
    }

    @GetMapping("/{id}")
    public GroupOutDto getGroupById(@PathVariable("id") Long id) {
        return groupsService.getGroupById(id);
    }

    @PostMapping
    public ResponseEntity createGroup(@Valid @RequestBody GroupInDto groupInDto) {
        groupsService.save(groupInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity updateGroup(@Valid @RequestBody GroupInDto groupInDto) {
        groupsService.update(groupInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/addStudents")
    public ResponseEntity addStudents(@RequestBody GroupInDto groupInDto) {
        groupsService.addStudents(groupInDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteGroup(@RequestBody GroupInDto groupInDto) {
        groupsService.delete(groupInDto);
        return ResponseEntity.ok().build();
    }
}
