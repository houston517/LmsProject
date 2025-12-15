package org.example.LMSProject.mapper;

import org.example.LMSProject.dto.CourseInDto;
import org.example.LMSProject.dto.CourseOutDto;
import org.example.LMSProject.model.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseOutDto toDto(Course course);
    Course toEntity(CourseInDto courseInDto);
    List<CourseOutDto> toDtoList(List<Course> courses);
}
