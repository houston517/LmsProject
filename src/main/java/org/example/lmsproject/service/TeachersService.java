package org.example.lmsproject.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.TeachersRepository;
import org.example.lmsproject.dto.TeacherInDto;
import org.example.lmsproject.dto.TeacherOutDto;
import org.example.lmsproject.exception.TeacherNotFoundException;
import org.example.lmsproject.mapper.TeacherMapper;
import org.example.lmsproject.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class TeachersService {
    private final TeachersRepository teachersRepository;
    private final TeacherMapper teacherMapper;

    public List<TeacherOutDto> getAll(Pageable pageable){
        return teacherMapper.toDtoList(teachersRepository.findAll(pageable).toList());
    }

    public Teacher findTeacherById(Long id){
        return teachersRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id.toString()));
    }

    @Transactional
    public void save(TeacherInDto teacherInDto) {
        teachersRepository.save(teacherMapper.toEntity(teacherInDto));
    }

    @Transactional
    public void update(TeacherInDto teacherInDto) {
        Teacher foundTeacher = teachersRepository.findById(teacherInDto.getId())
                .orElseThrow(() -> new TeacherNotFoundException(teacherInDto.getId().toString()));
        foundTeacher.setName(teacherInDto.getName());
        foundTeacher.setSurname(teacherInDto.getSurname());
        teachersRepository.save(foundTeacher);
    }

    @Transactional
    public void delete(TeacherInDto teacherInDto) {
        teachersRepository.deleteById(teacherInDto.getId());
    }
}
