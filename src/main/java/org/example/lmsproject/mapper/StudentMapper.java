package org.example.lmsproject.mapper;

import org.example.lmsproject.dto.StudentInDto;
import org.example.lmsproject.dto.StudentOutDto;
import org.example.lmsproject.model.Group;
import org.example.lmsproject.model.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    StudentOutDto toDto(Student student);
    Student toEntity(StudentInDto studentInDto);
    List<StudentOutDto> toDtoList(List<Student> students);

    default String map(Group group){
        if(group == null){return null;}
        return group.getName();
    }
}
