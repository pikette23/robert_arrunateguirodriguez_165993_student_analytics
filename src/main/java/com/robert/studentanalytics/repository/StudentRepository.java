package com.robert.studentanalytics.repository;

import com.robert.studentanalytics.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
