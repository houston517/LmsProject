package org.example.LMSProject.dao;

import org.example.LMSProject.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<Teacher, Long> {
}
