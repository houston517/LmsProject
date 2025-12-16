package org.example.lmsproject.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.StudentsRepository;
import org.example.lmsproject.dto.StudentInDto;
import org.example.lmsproject.dto.StudentOutDto;
import org.example.lmsproject.mapper.StudentMapper;
import org.example.lmsproject.model.Group;
import org.example.lmsproject.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.lmsproject.exception.StudentNotFountException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class StudentsService {

    private final StudentsRepository studentsRepository;
    private final StudentMapper studentMapper;

    public List<StudentOutDto> getAll(Pageable pageable) {
        return studentMapper.toDtoList(studentsRepository.findAll(pageable).toList());
    }

    @Transactional
    public void save(StudentInDto studentInDto) {
        studentsRepository.save(studentMapper.toEntity(studentInDto));
    }

    @Transactional
    public void update(StudentInDto studentInDto) {
        Student foundStudent = studentsRepository.findById(studentInDto.getId())
                .orElseThrow(() -> new StudentNotFountException(studentInDto.getId().toString()));
        foundStudent.setName(studentInDto.getName());
        foundStudent.setSurname(studentInDto.getSurname());
        studentsRepository.save(foundStudent);
    }

    public void setGroup(Long studentId , Group group){
        Student foundStudent = studentsRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFountException(studentId.toString()));
            foundStudent.setGroup(group);
            studentsRepository.save(foundStudent);
    }
    @Transactional
    public void delete(StudentInDto studentInDto) {
        studentsRepository.deleteById(studentInDto.getId());
    }
}
