package org.example.LMSProject.service;

import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dao.CoursesRepository;
import org.example.LMSProject.dto.CourseInDto;
import org.example.LMSProject.dto.CourseOutDto;
import org.example.LMSProject.mapper.CourseMapper;
import org.example.LMSProject.model.Course;
import org.example.LMSProject.model.Group;
import org.example.LMSProject.model.Teacher;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CoursesService {
    private final CoursesRepository coursesRepository;
    private final CourseMapper courseMapper;
    private final GroupsService groupsService;
    private final TeachersService teachersService;

    public List<CourseOutDto> getAllCourses(){
        return courseMapper.toDtoList(coursesRepository.findAll());
    }

    public Course findCourseById(Long id){
        return coursesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(CourseInDto courseInDto) {
        Course course = courseMapper.toEntity(courseInDto);
        coursesRepository.save(course);
    }

    @Transactional
    public void update(CourseInDto courseInDto) {
        Course foundCourse = coursesRepository.findById(courseInDto.getId()).orElse(null);
        if(foundCourse != null){
            foundCourse.setName(courseInDto.getName());
            foundCourse.setDescription(courseInDto.getDescription());
            coursesRepository.save(foundCourse);
        }
    }

    @Transactional
    public void delete(CourseInDto courseInDto) {
        coursesRepository.deleteById(courseInDto.getId());
    }
}
