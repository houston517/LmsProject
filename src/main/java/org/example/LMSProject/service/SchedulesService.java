package org.example.LMSProject.service;

import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dao.SchedulesRepository;
import org.example.LMSProject.dto.ScheduleInDto;
import org.example.LMSProject.dto.ScheduleOutDto;
import org.example.LMSProject.mapper.ScheduleMapper;
import org.example.LMSProject.model.Course;
import org.example.LMSProject.model.Group;
import org.example.LMSProject.model.Schedule;
import org.example.LMSProject.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class SchedulesService {
    private final SchedulesRepository schedulesRepository;
    private final ScheduleMapper scheduleMapper;
    private final GroupsService groupsService;
    private final TeachersService teachersService;
    private final CoursesService coursesService;

    public List<ScheduleOutDto> getAllSchedules() {
        return scheduleMapper.toDtoList(schedulesRepository.findAll());
    }

    public List<ScheduleOutDto> getSchedulesByTeacherId(Long teacherId) {
        return scheduleMapper.toDtoList(schedulesRepository.getSchedulesByTeacherId(teacherId));
    }
    public List<ScheduleOutDto> getSchedulesByGroupId(Long groupId) {
        return scheduleMapper.toDtoList(schedulesRepository.getSchedulesByGroupId(groupId));
    }

   public List<ScheduleOutDto> getSchedulesByGroupIdAndTeacherId(Long groupId, Long teacherId) {
        return scheduleMapper.toDtoList(schedulesRepository.getSchedulesByGroupIdAndTeacherId(groupId, teacherId));
   }

    @Transactional
    public void save(ScheduleInDto scheduleInDto){
        Schedule schedule = scheduleMapper.toEntity(scheduleInDto);
        if(scheduleInDto.getGroupId() != null){
            Group group = groupsService.findGroupById(scheduleInDto.getGroupId());
            schedule.setGroup(group);
        }
        if(scheduleInDto.getTeacherId()!= null){
            Teacher teacher = teachersService.findTeacherById(scheduleInDto.getTeacherId());
            schedule.setTeacher(teacher);
        }
        if(scheduleInDto.getCourseId()!= null){
            Course course = coursesService.findCourseById(scheduleInDto.getCourseId());
            schedule.setCourse(course);
        }
        schedulesRepository.save(schedule);
    }

    @Transactional
    public void update(ScheduleInDto scheduleInDto){
        Schedule foundSchedule = schedulesRepository.findById(scheduleInDto.getId()).orElse(null);
        if(foundSchedule != null){
            if(scheduleInDto.getGroupId() != null){
                Group group = groupsService.findGroupById(scheduleInDto.getGroupId());
                foundSchedule.setGroup(group);
            }
            if(scheduleInDto.getTeacherId()!= null){
                Teacher teacher = teachersService.findTeacherById(scheduleInDto.getTeacherId());
                foundSchedule.setTeacher(teacher);
            }
            if(scheduleInDto.getCourseId()!= null){
                Course course = coursesService.findCourseById(scheduleInDto.getCourseId());
                foundSchedule.setCourse(course);
            }
            if(scheduleInDto.getLessonDate()!= null){
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(scheduleInDto.getLessonDate(),dateFormat);
                foundSchedule.setLessonDate(localDateTime);
            }
            schedulesRepository.save(foundSchedule);
        }
    }
    @Transactional
    public void delete(ScheduleInDto scheduleInDto){
        schedulesRepository.deleteById(scheduleInDto.getId());
    }
}
