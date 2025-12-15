package org.example.LMSProject.dao;

import org.example.LMSProject.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course,Long> {
}
