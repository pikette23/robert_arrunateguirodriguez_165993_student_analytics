package com.robert.studentanalytics.batch.reader;

import com.robert.studentanalytics.model.Student;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
/**/
/*Cada etapa Job se divide en tres partes. Estamos en la de Reader*/

@Configuration // La configuración del Job y sus Steps
public class StudentReader {
    @Bean
    public FlatFileItemReader<Student> studentItemReader(){
        return new FlatFileItemReaderBuilder<Student>()
                .name("studentItemReader")//Le damos nombre al reader
                .resource(new ClassPathResource("students.csv"))//Indicamos donde se encuentra el archivo para leerlo
                .delimited()// Aquí digo que el archivo tiene columnas separadas por comas
                .names("id","name","averageGrade")//Definimos los nombres de la columna del CSV
                .linesToSkip(1)
                .targetType(Student.class)// Cada línea del CSV debe convertirse en un objeto Student y se lo damos al processor
                .build();
    }
}
