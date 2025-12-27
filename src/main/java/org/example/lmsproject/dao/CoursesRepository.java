package org.example.lmsproject.dao;

import org.example.lmsproject.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course,Long> {
}
