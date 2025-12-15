package org.example.LMSProject.dao;

import org.example.LMSProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Student,Long> {

}
