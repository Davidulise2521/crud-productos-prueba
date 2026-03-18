# CRUD Productos - Frontend

Interfaz web desarrollada en **Angular 11** para el consumo de la API REST del CRUD de Productos. Utiliza **Bootstrap 5** para un diseño moderno, responsivo y ordenado.

## Tecnologías Principales
- **Angular CLI 11.0.7**
- **Node.js 16**
- **RxJS** (Manejo de asincronía y Observables)
- **Bootstrap 5.3** (Vía CDN)

## Arquitectura del Proyecto Frontend
La aplicación fue diseñada siguiendo las especificaciones estrictas para mantener desacoplamiento:
1. **Models (`/models`):** Contiene las interfaces TypeScript que replican los DTOs del servidor asegurando tipado fuerte.
2. **Services (`/services`):** El `ProductoService` centraliza las peticiones `HttpClient` delegándolas a componentes, mientras que `AuthService` simula la sesión. Todos devuelven Observables.
3. **Interceptors (`/interceptors`):** Se interceptan todas las peticiones salientes (`JwtInterceptor`) para incrustar el Token Bearer simulado automáticamente sin afectar la lógica individual.
4. **Guards (`/guards`):** `AuthGuard` verifica las variables de entorno de sesión antes de que el router pinte las vistas.
5. **Components (`/components`):** 
   - `LoginComponent`: Simulación de autenticación local.
   - `ProductoListComponent`: Renderiza la tabla de métricas y controles con paginación integrada.
   - `ProductoFormComponent`: Módulo de control Reactivo y por Templates para la inserción y edición.

## Instalación y Ejecución

*Nota: Requiere tener la API de Spring Boot encendida nativamente en `localhost:8080`.*

```bash
# Entrar a la carpeta
cd frontend

# Instalar los paquetes (Se usa legacy-peer-deps por ser versión estricta de Angular 11)
npm install --legacy-peer-deps

# Compilar y arrancar el servidor en caliente
npx -p @angular/cli@11 ng serve -o
```

Navegar a `http://localhost:4200/`.

**Credenciales simuladas:**
- Correo: `admin@crud.com`
- Clave: `admin`
