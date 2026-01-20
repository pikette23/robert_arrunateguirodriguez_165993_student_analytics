package com.robert.studentanalytics.batch.writer;

import com.robert.studentanalytics.model.Student;
import com.robert.studentanalytics.repository.StudentRepository;
import com.robert.studentanalytics.service.StudentService;
import com.robert.studentanalytics.service.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class StudentWriter implements ItemWriter<Student> {
    private final StudentService studentService;

    //EL writer usará el repositorio JPA para insertar estudiantes en la tabla student
    @Override
    public void write(Chunk<? extends Student> chunk){
        studentService.guardarTodo((List<Student>) chunk.getItems());//getItem me devuelve la lista real de Students
    }




}
