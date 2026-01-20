# Student Analytics
Es un proyecto desarrollado en Spring Boot que combina **WebFlux**, **patrones de microservicios**, **Spring Batch** y **trazabilidad ligera**.
Todo el proyecto se ejecutará en una única aplicación.

## Cómo arrancar la aplicación

1. Clonar el repositorio: git clone https://github.com/pikette23/robert_arrunateguirodriguez_165993_student_analytics.git
2. Entrar en el proyecto: cd robert_arrunateguirodriguez_165993_student_analytics 
3. Ejecutar la aplicación: mvn spring-boot:run

La aplicación se inicia en: **http://localhost:8080**

## Cómo lanzar peticiones
Obtener estudiantes mediante API Gateway:

**Obtener todos los estudiantes:**

GET http://localhost:8080/students

**Obtener estudiantes con nota mínima** 

GET http://localhost:8080/students/top?min=7.5

**Obtener un estudiante por ID**

GET http://localhost:8080/students/obtener/1

**Borrar un estudiante por ID**

DELETE http://localhost:8080/students/1

**Obtener estudiantes a través del API Gateway (ruta pública)**

GET http://localhost:8080/api/public/students




## Microservicios/Patrones
Aunque toda la aplicación está dentro de un único proyecto, simularemos tres patrones.

**API GATEWAY:**
El **GatewayController** actúa como un único punto de entrada para los clientes.
Cuando el cliente llama **/api/public/students**, el Gateway no devuelve directamente los datos, y esto es muy importante,
sino que llama internamente al StudentController usando **WebClient** mediante **GatewayService**.

Esto es lo que hemos estado buscando, que el cliente nunca llame directamente al servicio interno, sino que pase por el Gateway primero.

**Circuit Breaker:**
Si el **StudentService** tarda demasiado, falla o no responde, el Circuit Breaker actúa. En el caso de cualquiera
de estos incovenientes se ejecuta un fallback que devuelve un **Flux.empty**.

Todo esto evita que el sistema entero falle.

**TraceID:**
Cada petición al Gateway genera un UUID que sirve como identificador de traza. 
1. Se imprime en los logs.
2. Se propaga durante el procesamiento con doOnNext y doOnComplete

Esto permite seguir toda la vida de una petición en los logs.

## Batch
Incluimos un Job de Spring Batch **importStudentsJob** que ejecutará al arrancar la aplicación.
Este Job contiene un Step **importStudentsJob** que procesa los datos del fichero **students.csv** en chunks de 5 elementos.

El Step realiza el flujo completo:

Reader → Processor → Writer → H2

* El Reader lee el CSV línea a línea
* El Processor transforma o valida cada Student
* El Writer guarda los datos en la tabla H2

Cada chunk es una transacción independiente, lo que garantiza un procesamiento robusto y eficiente.

## Parte reactiva (WebFlux)
En la aplicación utilizaremos **Spring Webflux** para exponer una API no bloqueante basada en flujos reactivos.

El **StudentController** devuelve Flux y Mono, que representan flujos de datos asíncronos.
* Las peticiones se procesan de forma eficiente 
* No se bloquean los hilos
* El Gateway consume esta API usando WebClient
* Se puede añadir trazas como bien hicimos en **GatewayController** sin interrumpir el flujo.
