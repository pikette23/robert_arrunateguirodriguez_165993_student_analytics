package com.robert.studentanalytics.batch.processor;

import com.robert.studentanalytics.model.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student){
        //Pondremos nombre en mayúscula
        student.setName(student.getName().toUpperCase());

        return student;
    }
}
