package org.example.lmsproject.dao;

import org.example.lmsproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Student,Long> {

}
