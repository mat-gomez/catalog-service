<!-- evaluacion:inicio -->
# Evaluación — mat-gomez/catalog-service

> Documento **generado automáticamente** por la cátedra de Programación II (UM): RepoSense + análisis de calidad de **7 dimensiones**. **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas) + build: es una criba para orientar la revisión, **no una nota**. La **documentación** del repositorio no se evalúa.
> Generado: 2026-10-19.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad | **35/100** |
| Tipo de repositorio | `backend` |
| Build (`mvnw verify` / `gradlew check`) | no compila |
| Código (LOC) | 319 |
| Archivos de código | 15 |
| Clases | 15 |
| Código dentro de clases (%POO) | 66% |
| Funciones fuera de clases | 0 |
| Duplicación (puerta) | 0.0% |
| Duplicación lógica | 0.0% |
| Duplicación de boilerplate | 0.0% |
| Tope duro aplicado a D1 | `POO_BAJO` → D1 ≤ 60 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

## 2. Semáforo de aprobación

Criterios de la cátedra (`CRITERIOS-CALIDAD.md`): **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **no cumple** — 4/11 |
| Destacar (`exigente`) | **no cumple** — 2/11 |

**Para aprobar, falta cumplir:** Hexagonal; SQL; Tests

**Para destacar, falta cumplir:** SOLID; POO; Hexagonal; SQL; Tests

## 3. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| Melina Abril Gomez Torres | 11 | 361 | 13 | 1 | 5 | 100% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis (que mira el estado de `main`)._

## 4. Dimensiones evaluadas

Cada dimensión indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el puntaje queda por debajo de la referencia, se agregan preguntas para **investigar la causa** (no hay recetas).

| Dimensión | Peso | Puntaje |
|:--|--:|--:|
| **D1** Diseño OO, SOLID y patrones (20%) | 20% | 17 |
| **D2** Arquitectura hexagonal — backend (15%) | 15% | 0 |
| **D3** Frontend KMP: MVVM y buenas prácticas (10%) | 10% | N/A |
| **D4** Integración y contrato (10%) | 10% | 60 |
| **D5** Seguridad y robustez (10%) | 10% | 70 |
| **D6** Pruebas y cobertura (20%) | 20% | 2 |
| **D7** Proceso y entrega (15%) | 15% | 100 |

### D1. Diseño OO, SOLID y patrones (20%)

- **Qué mide:** Métricas orientadas a objetos (clases, métodos, campos, cohesión, acoplamiento, herencia) y proxies de SOLID, más patrones de diseño.
- **Por qué importa:** El eje de la materia es el buen uso de la POO y su arquitectura: un diseño con clases claras y poco acopladas es más fácil de entender, probar y cambiar.
- **Tu resultado:** **17/100**
- **Para investigar la causa:**
    - ¿Se pensó el modelo de clases antes de codificar o se agrupó después?
    - ¿Qué clases concentran muchas responsabilidades a la vez?
    - ¿Los colaboradores llegan como parámetros o se crean por dentro?

### D2. Arquitectura hexagonal — backend (15%)

- **Qué mide:** Separación en capas (dominio, puertos de entrada/salida, adaptadores) y dirección de las dependencias del backend.
- **Por qué importa:** Aislar el dominio de la infraestructura permite cambiar la base de datos, el framework o el transporte sin tocar las reglas de negocio.
- **Tu resultado:** **0/100**
- **Para investigar la causa:**
    - ¿El dominio importa framework o detalles de persistencia?
    - ¿Dónde vive la lógica de negocio frente a los controladores?
    - ¿Se definieron puertos para los servicios que se consumen?

### D4. Integración y contrato (10%)

- **Qué mide:** Cumplimiento del contrato de integración de la cátedra (formato de errores, paginación, autenticación, endpoints esperados).
- **Por qué importa:** Los servicios deben poder integrarse entre sí y con la app sin sorpresas; el contrato es lo que hacen cumplir las pruebas de integración.
- **Tu resultado:** **60/100**

### D5. Seguridad y robustez (10%)

- **Qué mide:** Credenciales embebidas, consultas SQL por concatenación, y presencia de config de entorno; aislamiento de secretos.
- **Por qué importa:** Un secreto en el repositorio o una consulta construida por concatenación son riesgos concretos, no hipotéticos.
- **Tu resultado:** **70/100**

### D6. Pruebas y cobertura (20%)

- **Qué mide:** Cantidad y resultado de pruebas y cobertura de línea (JaCoCo/Kover, Surefire/Failsafe) cuando hay build.
- **Por qué importa:** Las pruebas son la red de seguridad para refactorizar e integrar; la cobertura acota las zonas sin verificar.
- **Tu resultado:** **2/100**
- **Para investigar la causa:**
    - ¿Qué partes del sistema no tienen pruebas?
    - ¿Las pruebas cubren caminos de error o solo el caso feliz?
    - ¿Qué falla al ejecutar la suite completa (no solo la del módulo propio)?

### D7. Proceso y entrega (15%)

- **Qué mide:** Artefactos verificables de proceso/entrega: `compose`, `.env.example`, CI (`.github/workflows/`), `CODEOWNERS` y wrapper de build (`mvnw`/`gradlew`). **No** puntúa documentación.
- **Por qué importa:** Un proyecto que compila, se levanta y se valida solo es más fácil de revisar y de integrar en equipo.
- **Tu resultado:** **100/100**

## 5. Señales detectadas

El análisis encontró las siguientes señales (archivo, sin número de línea a propósito):

**DIP/concretos** (3)
- clase 'CatalogController' muy acoplada (CBO=13) — `src/main/java/ar/edu/um/catalog/controller/CatalogController.java`
- clase 'ProfessionalRepository' muy acoplada (CBO=12) — `src/main/java/ar/edu/um/catalog/repository/ProfessionalRepository.java`
- clase 'CatalogSyncService' muy acoplada (CBO=23) — `src/main/java/ar/edu/um/catalog/service/CatalogSyncService.java`

**SRP/responsabilidad** (8)
- clase 'CatalogSyncService' con baja cohesión (10%) — `src/main/java/ar/edu/um/catalog/service/CatalogSyncService.java`
- método 'CatalogSyncService.executeFullSnapshotSync' de 71 líneas — `src/main/java/ar/edu/um/catalog/service/CatalogSyncService.java`
- método 'CatalogSyncService.executeFullSnapshotSync' con complejidad 8 — `src/main/java/ar/edu/um/catalog/service/CatalogSyncService.java`
- clase 'WeeklySchedule' grande (LOC=18, métodos=0, campos=9) — `src/main/java/ar/edu/um/catalog/domain/WeeklySchedule.java`
- clase 'WeeklySchedule' sin métodos (9 campos): solo datos — `src/main/java/ar/edu/um/catalog/domain/WeeklySchedule.java`
- clase 'ProfessionalCategory' sin métodos (6 campos): solo datos — `src/main/java/ar/edu/um/catalog/domain/ProfessionalCategory.java`
- clase 'Professional' sin métodos (7 campos): solo datos — `src/main/java/ar/edu/um/catalog/domain/Professional.java`
- clase 'CatalogSyncState' sin métodos (5 campos): solo datos — `src/main/java/ar/edu/um/catalog/domain/CatalogSyncState.java`

**arquitectura** (1)
- consulta SQL construida por concatenación/interpolación — `src/main/java/ar/edu/um/catalog/repository/ProfessionalRepository.java`

**hexagonal** (7)
- domain importa framework/infra: import jakarta.persistence.*; — `src/main/java/ar/edu/um/catalog/domain/WeeklySchedule.java`
- domain importa framework/infra: import jakarta.persistence.*; — `src/main/java/ar/edu/um/catalog/domain/ProcessedKafkaEvent.java`
- domain importa framework/infra: import jakarta.persistence.*; — `src/main/java/ar/edu/um/catalog/domain/ProfessionalCategory.java`
- domain importa framework/infra: import jakarta.persistence.*; — `src/main/java/ar/edu/um/catalog/domain/Professional.java`
- domain importa framework/infra: import jakarta.persistence.*; — `src/main/java/ar/edu/um/catalog/domain/CatalogSyncState.java`
- falta ports/in (hexagonal)
- falta ports/out (hexagonal)

## 6. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (6 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice de calidad a lo largo de los commits"
    x-axis ["c37d3a9", "89cef6e", "ea29b79", "8e540fc", "61e611e", "5fd1ac8"]
    y-axis "Índice" 0 --> 100
    line [0, 15, 35, 42, 30, 35]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-09-06 | `c37d3a9` | Melina Abril Gomez Torres | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-09-10 | `89cef6e` | Ing. Daniel Quinteros | 0 | 15 | 0% | 0.0% | 0 |
| 3 | 2026-10-15 | `ea29b79` | Melina Abril Gomez Torres | 18 | 35 | 56% | 0.0% | 0 |
| 4 | 2026-10-16 | `8e540fc` | Melina Abril Gomez Torres | 18 | 42 | 56% | 0.0% | 0 |
| 5 | 2026-10-18 | `61e611e` | Melina Abril Gomez Torres | 319 | 30 | 66% | 0.0% | 0 |
| 6 | 2026-10-18 | `5fd1ac8` | Melina Abril Gomez Torres | 319 | 35 | 66% | 0.0% | 0 |

---
_Informe generado por la cátedra. Las dimensiones y umbrales corresponden a `calidad.py` (perfil `exigente`); el semáforo, a `CRITERIOS-CALIDAD.md`. La documentación del repositorio no se evalúa. Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
