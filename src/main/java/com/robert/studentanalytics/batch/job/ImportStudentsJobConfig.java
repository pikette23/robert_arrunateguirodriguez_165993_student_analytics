package com.robert.studentanalytics.batch.job;

import com.robert.studentanalytics.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportStudentsJobConfig {
    @Bean
    public Step importStudentsStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<Student> reader,
                                   ItemProcessor<Student, Student> processor,
                                   ItemWriter<Student> writer) {

        return new StepBuilder("importStudentsStep", jobRepository)
                .<Student, Student>chunk(5, transactionManager)// Los 5 estudiantes, procésalos, escríbelos, y se empieza otro bloque, transaction.. controla que cada chunck sea una transacción independiente
                .reader(reader)//se lee línea por línea del csv
                .processor(processor)//Transforma el Student
                .writer(writer)//Guarda los estudiantes en la base de datos
                .build();
    }

    @Bean
    public Job importStudentsJob(JobRepository jobRepository,
                                 Step importStudentsStep) {

        return new JobBuilder("importStudentsJob", jobRepository)// creamos un Job
                .start(importStudentsStep)//define el primer Step del Job
                .build();
    }
}
