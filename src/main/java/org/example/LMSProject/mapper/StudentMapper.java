package org.example.LMSProject.mapper;

import org.example.LMSProject.dto.StudentInDto;
import org.example.LMSProject.dto.StudentOutDto;
import org.example.LMSProject.model.Group;
import org.example.LMSProject.model.Student;
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
