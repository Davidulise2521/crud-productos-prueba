# CRUD Productos - Backend

Backend desarrollado en **Java 21** y **Spring Boot 3.2.x** para la gestión de productos. Implementa un diseño estricto en capas (Controller, Service, Repository, Entity, DTO, Mapper) asegurando el principio de responsabilidad única y buenas prácticas de Clean Code.

## Tecnologías Principales
- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Data JPA** (Hibernate)
- **MySQL / H2 Database** (Soporta ambos perfiles)
- **Spring Boot Validation**
- **Lombok**
- **JUnit 5 & Mockito** (Testing)
- **JaCoCo** (Cobertura de Código)

## Arquitectura y Capas
1. **Controllers (`/controller`):** Expone la API RESTful. Exclusivamente recibe DTOs, delega lógica al Service y retorna respuestas HTTP.
2. **Services (`/service`):** Contiene todas las reglas y lógica de negocio (validaciones de nombres únicos, prevenciones de existencias negativas). Administra las transacciones (`@Transactional`).
3. **Repositories (`/repository`):** Capa de acceso a datos que interactúa con la BD.
4. **Entities (`/entity`):** Objetos de dominio mapeados a la base de datos de manera aislada (no se exponen al cliente web).
5. **DTOs y Mappers (`/dto`, `/mapper`):** Responsables de transferir información de forma segura entre el Controller y el Service, protegiendo a las Entidades originales.
6. **Exceptions (`/exception`):** Manejo global de errores (Validaciones, NotFound, BadRequest) utilizando un `@RestControllerAdvice`.

## Ejecutar el Proyecto
El proyecto está configurado por defecto para usar una base de datos dinámica en memoria **H2**, por lo tanto no requiere instalación local de MySQL para funcionar y probarse de inmediato.

```bash
# Compilar el proyecto instanciando dependencias
mvn clean compile

# Ejecutar el servidor web (Correrá en el puerto 8080)
mvn spring-boot:run
```

Si deseas usar **MySQL**: 
1. Abre `/src/main/resources/application.yml`
2. Comenta la subsección `H2` y descomenta la inferior para `MySQL`.
3. Reemplaza el parámetro `password` si es necesario.

## Ejecución de Pruebas Unitarias y Reportes
El proyecto contiene pruebas unitarias (`@ExtendWith(MockitoExtension.class)`), capa WebMvcTest y Pruebas de Integración con H2 (`@SpringBootTest`).

```bash
mvn clean test jacoco:report
```
El reporte visual de cobertura se generará en: `/target/site/jacoco/index.html`.
