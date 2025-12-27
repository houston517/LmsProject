package org.example.lmsproject.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.CoursesRepository;
import org.example.lmsproject.dto.CourseInDto;
import org.example.lmsproject.dto.CourseOutDto;
import org.example.lmsproject.exception.CourseNotFoundException;
import org.example.lmsproject.mapper.CourseMapper;
import org.example.lmsproject.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CoursesService {
    private final CoursesRepository coursesRepository;
    private final CourseMapper courseMapper;

    public List<CourseOutDto> getAll(Pageable pageable){
        return courseMapper.toDtoList(coursesRepository.findAll(pageable).toList());
    }

    public Course findCourseById(Long id){
        return coursesRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id.toString()));
    }

    @Transactional
    public void save(CourseInDto courseInDto) {
        Course course = courseMapper.toEntity(courseInDto);
        coursesRepository.save(course);
    }

    @Transactional
    public void update(CourseInDto courseInDto) {
        Course foundCourse = coursesRepository.findById(courseInDto.getId())
                .orElseThrow(() -> new CourseNotFoundException(courseInDto.getId().toString()));
        foundCourse.setName(courseInDto.getName());
        foundCourse.setDescription(courseInDto.getDescription());
        coursesRepository.save(foundCourse);
    }

    @Transactional
    public void delete(CourseInDto courseInDto) {
        coursesRepository.deleteById(courseInDto.getId());
    }
}
