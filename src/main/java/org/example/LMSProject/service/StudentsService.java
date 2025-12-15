package org.example.LMSProject.service;

import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dao.StudentsRepository;
import org.example.LMSProject.dto.StudentInDto;
import org.example.LMSProject.dto.StudentOutDto;
import org.example.LMSProject.mapper.StudentMapper;
import org.example.LMSProject.model.Group;
import org.example.LMSProject.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class StudentsService {

    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper;

    public List<StudentOutDto> getAllStudents(){
        return studentMapper.toDtoList(studentsRepository.findAll());
    }

    @Transactional
    public void save(StudentInDto studentInDto) {
        studentsRepository.save(studentMapper.toEntity(studentInDto));
    }

    @Transactional
    public void update(StudentInDto studentInDto) {
        Student foundStudent = studentsRepository.findById(studentInDto.getId()).orElse(null);
        if(foundStudent != null){
            foundStudent.setName(studentInDto.getName());
            foundStudent.setSurname(studentInDto.getSurname());
            studentsRepository.save(foundStudent);
        }
    }

    public void setGroup(Long studentId , Group group){
        studentsRepository.findById(studentId).ifPresent(foundStudent -> {
            foundStudent.setGroup(group);
            studentsRepository.save(foundStudent);
        });
    }
    @Transactional
    public void delete(StudentInDto studentInDto) {
        studentsRepository.deleteById(studentInDto.getId());
    }
}
