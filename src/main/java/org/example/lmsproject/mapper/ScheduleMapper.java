package org.example.lmsproject.mapper;

import org.example.lmsproject.dto.ScheduleInDto;
import org.example.lmsproject.dto.ScheduleOutDto;
import org.example.lmsproject.model.Course;
import org.example.lmsproject.model.Group;
import org.example.lmsproject.model.Schedule;
import org.example.lmsproject.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {
    ScheduleOutDto toDto(Schedule schedule);
    Schedule toEntity(ScheduleInDto scheduleInDto);
    List<ScheduleOutDto> toDtoList(List<Schedule> schedules);

    default String map(Group group){
        if(group != null){
            return group.getName();
        }
        return null;
    }
    default String map(Teacher teacher){
        if(teacher != null){
            return teacher.getFullName();
        }
        return null;
    }

    default String map(Course course){
        if(course != null){
            return course.getName();
        }
        return null;
    }
    default LocalDateTime map(String date){
        if(date != null){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(date,dateFormat);
            return localDateTime;
        }
        return null;
    }

    default String map(LocalDateTime date){
        if(date != null){
            return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        }
        return null;
    }
}
