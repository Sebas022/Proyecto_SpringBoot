# 🛡️ API RESTful con Spring Boot, JWT y Swagger

Este proyecto fue desarrollado como parte de un examen técnico, y extendido para mostrar buenas prácticas en desarrollo backend con Java. Refleja un enfoque autodidacta, funcional y profesional, integrando seguridad, documentación y arquitectura moderna.

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security + JWT
- Swagger / OpenAPI
- JPA / Hibernate
- Maven
- H2 (base de datos embebida)

## 📦 Características

- Autenticación sin estado con JWT
- API RESTful documentada con Swagger
- Arquitectura en capas: Controller, Service, Repository
- Base de datos embebida con H2 para pruebas rápidas

## 🧪 Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Sebas022/Proyecto_SpringBoot.git

## 🔎 Accede a la documentación con Swagger
http://localhost:8080/swagger-ui.html

## 🗂️ Script de base de datos

El proyecto incluye un archivo `bdExamen.sql` en `src/main/resources` con la definición de tablas y relaciones. Puedes usarlo para crear la base en PostgreSQL u otra base relacional.

Para ejecutarlo manualmente:

1. Abre tu gestor de base de datos (ej. pgAdmin, DBeaver)
2. Crea una base de datos nueva
3. Ejecuta el script `bdExamen.sql` para generar las tablas
