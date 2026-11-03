<!-- calidad:inicio -->
![Calidad](https://img.shields.io/badge/Calidad-35%2F100-orange) ![Aprobar](https://img.shields.io/badge/Aprobar-no-red) ![Destacar](https://img.shields.io/badge/Destacar-no-lightgrey) ![Build](https://img.shields.io/badge/Build-no%20compila-red) ![Duplicación](https://img.shields.io/badge/Duplicaci%C3%B3n-0.0%25-green) ![%POO](https://img.shields.io/badge/%25POO-66%25-yellow) ![Sueltas](https://img.shields.io/badge/Sueltas-0-green) ![D1](https://img.shields.io/badge/D1-17-red) ![D2](https://img.shields.io/badge/D2-0-red) ![D4](https://img.shields.io/badge/D4-60-yellow) ![D5](https://img.shields.io/badge/D5-70-yellow) ![D6](https://img.shields.io/badge/D6-2-red) ![D7](https://img.shields.io/badge/D7-100-green)

**Calidad (heurístico, 7 dimensiones):** índice **35/100** · build **no compila** · aprobar **no (4/11)** · destacar **no**
Detalle completo en [`docs/EVALUACION.md`](docs/EVALUACION.md).
<!-- calidad:fin -->

# catalog-service


# 📚 SERVICIO DE CATÁLOGO Y SINCRONIZACIÓN

> **Proyecto Integrador 2026 — Sistema Distribuido de Gestión de Turnos**

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-green?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Server-blue?style=for-the-badge&logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-Cache%2FSync-red?style=for-the-badge&logo=redis)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Messaging-black?style=for-the-badge&logo=apachekafka)
![Build Status](https://img.shields.io/badge/Build-SUCCESS-brightgreen?style=for-the-badge)

---

## 🚀 1. Descripción General

El **`catalog-service`** es un microservicio backend independiente desarrollado con **Java 21 y Spring Boot**, diseñado bajo los principios de **Arquitectura Hexagonal**. Su responsabilidad principal es actuar como la única fuente local vigente para consultar categorías, profesionales y horarios semanales, desacoplando las búsquedas de la API central de la cátedra.

Para mantener la consistencia de los datos, implementa una robusta **estrategia dual de sincronización** (completa mediante snapshots REST e incremental mediante eventos de Kafka y Redis).

---

## 🛠️ 2. Tecnologías y Stack

- **Core:** Java 21 (Amazon Corretto), Spring Boot 4.1.1.
- **Seguridad:** Spring Security OAuth2 Resource Server (Validación de tokens JWT) y CORS explícito.
- **Persistencia:** Spring Data JPA, Hibernate, PostgreSQL (Base de datos principal) y Flyway (Migraciones).
- **Mensajería y Caché:** Apache Kafka (Consumidor de eventos de sincronización) y Redis (Metadata e historial incremental).
- **Calidad y Pruebas:** JUnit 5, Mockito, Testcontainers y JaCoCo (Cobertura de código).
- **Contenedorización:** Docker y Docker Compose.

---

## ⚙️ 3. Requisitos Previos y Entorno

Para compilar y ejecutar el servicio localmente, asegúrate de contar con:
* **JDK 21** instalado en tu entorno.
* **Docker Engine** y **Docker Compose** (necesarios para levantar las instancias de PostgreSQL y Redis).
* El proyecto incluye **Maven Wrapper (`./mvnw`)**, por lo que no requieres instalar Maven de forma manual.

---

## 🔐 4. Configuración y Variables de Entorno

Toda la sensibilidad y parámetros de conexión se encuentran externalizados. Debes configurar las siguientes variables de entorno antes de ejecutar la aplicación:

| Variable de Entorno | Descripción |
| :--- | :--- |
| `SPRING_DATASOURCE_URL` | URL de conexión JDBC hacia PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos relacional |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de acceso a PostgreSQL |
| `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_SECRET_KEY` | Clave secreta simétrica para decodificar y validar el JWT técnico |
| `SPRING_DATA_REDIS_HOST` / `PORT` | Coordenadas de conexión al servidor Redis de la cátedra |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Servidores Bootstrap de Kafka |

---

## 🐳 5. Ejecución Local con Docker Compose

La infraestructura local de persistencia se despliega mediante contenedores aislados:

1. **Levantar los servicios dependientes:**
   ```bash
   docker compose up -d

```

2. **Ejecutar el microservicio Spring Boot:**
```bash
./mvnw spring-boot:run

```


*(Nota: Flyway se encargará de ejecutar automáticamente las migraciones de base de datos al iniciar).*

---

## 🔄 6. Estrategia de Sincronización del Catálogo

El servicio garantiza que la copia local esté siempre actualizada a través de dos mecanismos concurrentes:

1. **Sincronización Completa (Snapshot REST):**
* Se ejecuta ante una base vacía o una discontinuidad de versiones.


* Realiza una limpieza y reemplazo atómico protegido por transaccionalidad (`@Transactional`), respetando rigurosamente el orden de las claves foráneas (horarios $\rightarrow$ profesionales $\rightarrow$ categorías) para evitar violaciones de integridad.


2. **Sincronización Incremental (Kafka + Redis):**
* Escucha el tópico `CatalogUpdated`.


* Consulta los cambios diferenciales en Redis y aplica las actualizaciones secuencialmente, avanzando la versión local únicamente cuando la unidad de trabajo finaliza con éxito.





---

## 🧪 7. Pruebas Automatizadas y Calidad

El microservicio cuenta con una suite completa de pruebas unitarias y de integración que validan repositorios, seguridad, filtros de búsqueda y la lógica de sincronización.

Para ejecutar todas las pruebas y generar el reporte de cobertura con JaCoCo:

```bash
./mvnw clean verify

```

---

## 🛡️ 8. Seguridad y Autenticación

* **Protección de Endpoints:** Todos los recursos REST (excepto los de infraestructura/actuator) exigen una cabecera de autorización válida del tipo `Authorization: Bearer <jwt>`.


* **CORS:** Configurado explícitamente para permitir la integración segura con la interfaz gráfica de usuario en Kotlin Multiplatform (KMP).