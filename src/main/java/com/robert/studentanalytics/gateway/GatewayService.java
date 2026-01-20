package com.robert.studentanalytics.gateway;

import com.robert.studentanalytics.model.Student;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

//Aquí llamamos a la API reactiva
@Service
public class GatewayService {
    private final WebClient webClient;

    public GatewayService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }
    @CircuitBreaker(name = "students",fallbackMethod = "fallbackEstudiantes")
    public Flux<Student> obtenerEstudiantes(){
        return webClient.get().uri("/students").retrieve().bodyToFlux(Student.class).timeout(Duration.ofMillis(500));//Tiempo de espera
    }
    /* Hubiera estado bien también haberlo hecho de la manera convencional, es decir usando Spring Cloud Gateway*/
    //FALLBACK
    public Flux<Student> fallbackEstudiantes(Throwable e){
        System.out.println("Error: "+e.getMessage());
        return Flux.empty();
    }
}
