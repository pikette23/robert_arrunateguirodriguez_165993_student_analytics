package com.robert.studentanalytics.service;

import com.robert.studentanalytics.model.Student;
import com.robert.studentanalytics.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    @Override
    public Student guardar(Student s) {
        return studentRepository.save(s);
    }

    @Override
    public List<Student> guardarTodo(List<Student> students) {
        return studentRepository.saveAll(students);
    }

    @Override
    public Student obtenerPorId(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> obtenerTodos() {
        return studentRepository.findAll();
    }

    @Override
    public void borrarPorId(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void borrarTodos() {
        studentRepository.deleteAll();
    }

    @Override
    public List<Student> filtrarMin(double min) {
        return studentRepository.findAll().stream().filter(x -> x.getAverageGrade() >= min).toList();
    }
}
