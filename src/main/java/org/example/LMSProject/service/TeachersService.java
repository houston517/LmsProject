package org.example.LMSProject.service;

import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dao.TeachersRepository;
import org.example.LMSProject.dto.TeacherInDto;
import org.example.LMSProject.dto.TeacherOutDto;
import org.example.LMSProject.mapper.TeacherMapper;
import org.example.LMSProject.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class TeachersService {
    private final TeachersRepository teachersRepository;
    private final TeacherMapper teacherMapper;

    public List<TeacherOutDto> getAllTeachers(){
        return teacherMapper.toDtoList(teachersRepository.findAll());
    }

    public Teacher findTeacherById(Long id){
        return teachersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(TeacherInDto teacherInDto) {
        teachersRepository.save(teacherMapper.toEntity(teacherInDto));
    }

    @Transactional
    public void update(TeacherInDto teacherInDto) {
        Teacher foundTeacher = teachersRepository.findById(teacherInDto.getId()).orElse(null);
        if(foundTeacher != null){
            foundTeacher.setName(teacherInDto.getName());
            foundTeacher.setSurname(teacherInDto.getSurname());
            teachersRepository.save(foundTeacher);
        }
    }

    @Transactional
    public void delete(TeacherInDto teacherInDto) {
        teachersRepository.deleteById(teacherInDto.getId());
    }
}
