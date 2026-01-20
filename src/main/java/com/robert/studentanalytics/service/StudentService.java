package com.robert.studentanalytics.service;

import com.robert.studentanalytics.model.Student;

import java.util.List;

public interface StudentService {
    Student guardar(Student s);
    List<Student> guardarTodo(List<Student> students);
    Student obtenerPorId(Long id);
    List<Student> obtenerTodos();
    void borrarPorId(Long id);
    void borrarTodos();
    List<Student> filtrarMin(double min);
}
