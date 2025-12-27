package org.example.lmsproject.mapper;

import org.example.lmsproject.dto.CourseInDto;
import org.example.lmsproject.dto.CourseOutDto;
import org.example.lmsproject.model.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {
    CourseOutDto toDto(Course course);
    Course toEntity(CourseInDto courseInDto);
    List<CourseOutDto> toDtoList(List<Course> courses);
}
