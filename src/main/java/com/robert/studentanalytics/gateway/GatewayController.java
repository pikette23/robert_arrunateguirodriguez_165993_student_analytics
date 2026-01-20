package com.robert.studentanalytics.gateway;

import com.robert.studentanalytics.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/public/students")
public class GatewayController {
    private final GatewayService gatewayService;

    @GetMapping
    public Flux<Student> obtenerEstudiantes(){
        String traceId = UUID.randomUUID().toString();
        System.out.println("Trace-ID: "+traceId +"Obteniendo estudiantes..");
        return gatewayService.obtenerEstudiantes().doOnNext(v -> System.out.println("TRACE-ID" + traceId + " Procesando estudiantes: "+v.getName()))
                .doOnComplete(() -> System.out.println("TRACE-ID" + traceId + " Se ha completado la respuesta"));
    }

}
