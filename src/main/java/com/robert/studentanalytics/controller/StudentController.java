package com.robert.studentanalytics.controller;

import com.robert.studentanalytics.model.Student;
import com.robert.studentanalytics.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    @GetMapping
    public Flux<Student> listar(){
        return Flux.fromIterable(studentService.obtenerTodos());
    }
    @GetMapping("/top")
    public Flux<Student> listarPorNotaMedia(@RequestParam("min") double min){
        return Flux.fromIterable(studentService.filtrarMin(min));
    }
    @GetMapping("obtener/{id}")
    public Mono<Student> obtenerPorId(@PathVariable Long id) {
        return Mono.justOrEmpty(studentService.obtenerPorId(id));
    }
    @DeleteMapping("/{id}")
    public Mono<Void> borrar(@PathVariable Long id) {
        studentService.borrarPorId(id);
        return Mono.empty();
    }
}
