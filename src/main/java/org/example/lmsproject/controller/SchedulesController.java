package org.example.lmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dto.ScheduleInDto;
import org.example.lmsproject.dto.ScheduleOutDto;
import org.example.lmsproject.service.SchedulesService;
import org.example.lmsproject.util.LmsProjectUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("api/v1/schedules")
public class SchedulesController {
    private final SchedulesService schedulesService;

    @GetMapping()
    public List<ScheduleOutDto> getAll(@RequestParam(value = "groupId",required = false) Long groupId,
                                       @RequestParam(value = "teacherId", required = false) Long teacherId,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        Pageable pageable = LmsProjectUtil.getPageable(pageSize, pageNumber);
        return schedulesService.getSchedulesByQuery(groupId, teacherId, pageable);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ScheduleInDto scheduleInDto){
        schedulesService.save(scheduleInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity update(@Valid @RequestBody ScheduleInDto scheduleInDto){
        schedulesService.update(scheduleInDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestBody ScheduleInDto scheduleInDto){
        schedulesService.delete(scheduleInDto);
        return ResponseEntity.ok().build();
    }
}
