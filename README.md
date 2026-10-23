# Coworking Reservas — Microservicio REST

1. Descripción general
El proyecto consiste en el desarrollo de un microservicio REST para gestionar reservas de espacios de coworking, como salas de reuniones y puestos de trabajo.

El sistema permite administrar espacios, registrar usuarios, autenticar solicitudes, crear y cancelar reservas, validar pagos mediante un servicio externo simulado y consultar información de ocupación.

2. Tecnologías utilizadas y justificación

Tecnología	Justificación
Java 17+	Ofrece una plataforma estable y compatible con Spring Boot 3.
Spring Boot 3.x	Simplifica la configuración, integración y ejecución del microservicio.
Spring Web	Para poder desarrollar controladores y endpoints REST.
Spring Data JPA	Facilita la persistencia y las consultas a la base de datos.
Hibernate	Implementa el mapeo objeto-relacional entre entidades Java y tablas.
PostgreSQL	Proporciona persistencia relacional y soporte transaccional.
Spring Security	Centraliza la autenticación y autorización de solicitudes.
JWT	Permite autenticar peticiones mediante tokens firmados.
Jakarta Validation	Valida los datos recibidos en los DTO.
MapStruct	Facilita la conversión entre entidades y DTO.
OpenFeign	Simplifica la comunicación HTTP con servicios externos.
Spring Cache	Reduce consultas repetidas en los reportes de ocupación.
Spring Async	Permite ejecutar notificaciones en segundo plano.
Spring Boot Actuator	Expone información de salud y monitoreo de la aplicación.
Resilience4j	Aporta tolerancia a fallos en integraciones externas.
Springdoc OpenAPI	Genera documentación interactiva de los endpoints.

3. Estructura del proyecto
El proyecto está organizado mediante una **arquitectura en capas**, donde cada paquete tiene una responsabilidad específica. Esto permite mantener el código ordenado, facilitar su mantenimiento y separar la lógica de negocio de las demás funcionalidades.

- **clients:** contiene los clientes HTTP utilizados para comunicarse con servicios externos, como la validación de pagos mediante OpenFeign.
- **controllers:** se encarga de recibir las peticiones HTTP y devolver las respuestas de la API.
- **dto:** contiene los objetos utilizados para recibir y enviar información, además de los mapeadores para convertir entre DTO y entidades.
- **entities:** contiene las entidades JPA que representan las tablas de la base de datos.
- **exceptions:** agrupa las excepciones personalizadas y el manejo centralizado de errores.
- **repositories:** permite realizar consultas y operaciones sobre la base de datos utilizando Spring Data JPA.
- **security:** contiene la configuración de Spring Security, autenticación JWT y autorización por roles.
- **services:** contiene la lógica de negocio, como la creación de reservas, validación de disponibilidad y procesamiento de pagos.
- **resources:** almacena los archivos de configuración YAML para los diferentes ambientes.


4. Trade-offs
Una de las principales decisiones fue priorizar una solución funcional y organizada antes que implementar una arquitectura demasiado compleja.

Por ejemplo, no consideré necesario incorporar herramientas como Eureka, API Gateway o un sistema de mensajería, ya que habrían aumentado el tiempo de desarrollo y la configuración del proyecto.

También opté por utilizar una caché local y servicios simulados en lugar de integrar soluciones externas completas.

Estas decisiones permitieron concentrar el esfuerzo en las funcionalidades principales del microservicio, manteniendo una estructura que pueda ampliarse posteriormente.

Como mejoras futuras, consideraría incorporar una caché distribuida, un sistema de mensajería para las notificaciones, pruebas de concurrencia más completas y mecanismos adicionales de seguridad y monitoreo.


4. Funcionalidades fuera de alcance y mejoras futuras

Debido al tiempo limitado para desarrollar la prueba técnica, no fue posible completar todos los requisitos solicitados. Se decidió priorizar la implementación de las funcionalidades principales del microservicio, la seguridad, las validaciones y las reglas de negocio.

Los puntos que quedaron pendientes fueron:

- **Pruebas de integración:** no se alcanzaron a implementar las dos pruebas de integración solicitadas utilizando @SpringBootTest con Testcontainers o una base de datos en memoria. 

- **Docker:** no se logró completar la creación y validación de un Dockerfile funcional para ejecutar el microservicio en un contenedor. Como mejora, implementaría también un archivo `docker-compose.yml` para levantar la aplicación junto con PostgreSQL de manera más sencilla.

5. El siguiente enlace la Colección Postman, de igual manera se incluyo en elrepositorio un archivo llamado prueba-tecnica el cual contiene la coleccion para poder ser importado a postman.

https://satellite-meteorologist-33473820-s-team.postman.co/workspace/My-Workspace~bf4534ec-3c5f-4144-8637-a07b65925628/collection/37007959-ddcc677e-f476-4a83-abf5-4bb1a1160e6c?action=share&creator=37007959
