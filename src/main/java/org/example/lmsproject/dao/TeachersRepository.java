package org.example.lmsproject.dao;

import org.example.lmsproject.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<Teacher, Long> {
}
