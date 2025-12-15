package org.example.LMSProject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dto.ScheduleInDto;
import org.example.LMSProject.dto.ScheduleOutDto;
import org.example.LMSProject.service.SchedulesService;
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
    public List<ScheduleOutDto> getAllSchedules(@RequestParam(value = "groupId",required = false) Long groupId,
                                                @RequestParam(value = "teacherId", required = false) Long teacherId) {
        if(groupId != null && teacherId != null){
            return schedulesService.getSchedulesByGroupIdAndTeacherId(groupId, teacherId);
        } else if(groupId != null){
            return schedulesService.getSchedulesByGroupId(groupId);
        } else if(teacherId != null){
            return schedulesService.getSchedulesByTeacherId(teacherId);
        } else {
            return schedulesService.getAllSchedules();
        }
    }

    @PostMapping
    public ResponseEntity createSchedule(@Valid @RequestBody ScheduleInDto scheduleInDto){
        schedulesService.save(scheduleInDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity updateSchedule(@Valid @RequestBody ScheduleInDto scheduleInDto){
        schedulesService.update(scheduleInDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity deleteSchedule(@RequestBody ScheduleInDto scheduleInDto){
        schedulesService.delete(scheduleInDto);
        return ResponseEntity.ok().build();
    }
}
