package org.example.lmsproject.mapper;

import org.example.lmsproject.dto.TeacherInDto;
import org.example.lmsproject.dto.TeacherOutDto;
import org.example.lmsproject.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {
    TeacherOutDto toDto(Teacher teacher);
    Teacher toEntity(TeacherInDto teacherInDto);
    List<TeacherOutDto> toDtoList(List<Teacher> teachers);
}
