package org.example.lmsproject.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.SchedulesRepository;
import org.example.lmsproject.dto.ScheduleInDto;
import org.example.lmsproject.dto.ScheduleOutDto;
import org.example.lmsproject.exception.ScheduleNotFoundException;
import org.example.lmsproject.mapper.ScheduleMapper;
import org.example.lmsproject.model.Course;
import org.example.lmsproject.model.Group;
import org.example.lmsproject.model.Schedule;
import org.example.lmsproject.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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

   public List<ScheduleOutDto> getSchedulesByQuery(Long groupId, Long teacherId, Pageable pageable) {
        return scheduleMapper.toDtoList(schedulesRepository.getSchedulesByQuery(groupId, teacherId, pageable));
   }

    @Transactional
    public void save(ScheduleInDto scheduleInDto){
        Schedule schedule = scheduleMapper.toEntity(scheduleInDto);
        setDependencies(scheduleInDto, schedule);
        schedulesRepository.save(schedule);
    }

    private void setDependencies(ScheduleInDto scheduleInDto, Schedule schedule) {
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
    }

    @Transactional
    public void update(ScheduleInDto scheduleInDto){
        Schedule foundSchedule = schedulesRepository.findById(scheduleInDto.getId())
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleInDto.getId().toString()));
        setDependencies(scheduleInDto, foundSchedule);
        if(scheduleInDto.getLessonDate()!= null){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(scheduleInDto.getLessonDate(),dateFormat);
            foundSchedule.setLessonDate(localDateTime);
        }
        schedulesRepository.save(foundSchedule);
    }
    @Transactional
    public void delete(ScheduleInDto scheduleInDto){
        schedulesRepository.deleteById(scheduleInDto.getId());
    }

    @Scheduled(cron = "0 0 0 ? * *")
    @Transactional
    public void deleteYearOldSchedules(){
        schedulesRepository.deleteByLessonDateBefore(LocalDateTime.now().minusYears(1));
    }
}
