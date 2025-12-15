package org.example.LMSProject.mapper;

import org.example.LMSProject.dto.TeacherInDto;
import org.example.LMSProject.dto.TeacherOutDto;
import org.example.LMSProject.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {
    TeacherOutDto toDto(Teacher teacher);
    Teacher toEntity(TeacherInDto teacherInDto);
    List<TeacherOutDto> toDtoList(List<Teacher> teachers);
}
