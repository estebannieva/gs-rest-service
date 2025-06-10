# Inditex API

[![build](https://github.com/estebannieva/gs-rest-service/actions/workflows/build.yml/badge.svg)](https://github.com/estebannieva/gs-rest-service/actions/workflows/build.yml)
[![tests](https://github.com/estebannieva/gs-rest-service/actions/workflows/test.yml/badge.svg)](https://github.com/estebannieva/gs-rest-service/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/estebannieva/gs-rest-service/branch/main/graph/badge.svg)](https://codecov.io/gh/estebannieva/gs-rest-service)

Este proyecto es una API REST desarrollada con Spring Boot 3 y Java 21 para consultar el precio aplicable a un producto en función de la fecha, el producto y la marca.

## Índice

- [Principios y patrones aplicados](#principios-y-patrones-aplicados)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Ejecutar la aplicación](#ejecutar-la-aplicación)
- [Ejemplos de respuesta](#ejemplos-de-respuesta)
- [Ejecutar los tests](#ejecutar-los-tests)
- [Reporte de cobertura con JaCoCo](#reporte-de-cobertura-con-jacoco)
- [Cómo levantar SonarQube](#cómo-levantar-sonarqube)
- [Análisis con SonarQube](#análisis-con-sonarqube)

## Principios y patrones aplicados

- Arquitectura Hexagonal (Ports & Adapters): Separación clara entre el dominio de negocio y las tecnologías externas. El paquete `domain` contiene el núcleo y sus interfaces (`port`), mientras que `infrastructure` actúa como adaptador.
- Domain-Driven Design (DDD): División del dominio en agregados (`Price`, `PriceId`), uso de `UseCase` como entrada al dominio, y excepciones específicas como `PriceNotFoundException`.
- CQRS (Command Query Responsibility Segregation): Separación explícita de consultas (`FindPriceQuery`) y manejadores (`FindPriceHandler`), reforzando el aislamiento de responsabilidades.
- Query Handler: Implementado con `FindPriceHandler`, encargado de manejar la lógica de ejecución para una query.
- Clean Code: Clases cohesionadas, principios SOLID, nombres significativos y responsabilidad única en cada componente.
- Vertical Slicing: Organización por funcionalidad (`pricing`) en lugar de capas técnicas, lo que hace el código más claro y fácil de seguir.
- Screaming Architecture: Desde el primer vistazo al árbol de paquetes (`domain`, `application`, `infrastructure`, `query`, etc.), se entiende que este proyecto trata sobre gestión de precios.

## Estructura del proyecto

```
src
├── main
│   ├── java
│   │   └── com/example/restservice
│   │       ├── RestServiceApplication.java
│   │       ├── config
│   │       │   └── OpenApiConfig.java
│   │       └── pricing
│   │           ├── application
│   │           │   └── query
│   │           │       ├── FindPriceHandler.java
│   │           │       └── FindPriceQuery.java
│   │           ├── domain
│   │           │   ├── exception
│   │           │   │   └── PriceNotFoundException.java
│   │           │   ├── model
│   │           │   │   ├── Price.java
│   │           │   │   └── PriceId.java
│   │           │   └── port
│   │           │       ├── in
│   │           │       │   └── FindPriceUseCase.java
│   │           │       └── out
│   │           │           └── PriceRepository.java
│   │           ├── infrastructure
│   │           │   ├── controller
│   │           │   │   ├── PriceController.java
│   │           │   │   ├── dto
│   │           │   │   │   └── ErrorResponse.java
│   │           │   │   └── handler
│   │           │   │       └── RestExceptionHandler.java
│   │           │   ├── mapper
│   │           │   │   └── PriceMapper.java
│   │           │   └── persistence
│   │           │       ├── JpaPriceRepository.java
│   │           │       ├── JpaPriceRepositoryAdapter.java
│   │           │       └── PriceEntity.java
│   │           └── query
│   │               └── dto
│   │                   └── PriceResponse.java
│   └── resources
│       ├── application.yaml
│       ├── data.sql
│       └── schema.sql
└── test
    └── java
        └── com/example/restservice
            ├── RestServiceApplicationTests.java
            ├── application
            │   └── query
            │       └── FindPriceHandlerTests.java
            ├── domain
            │   └── model
            │       ├── PriceIdTests.java
            │       └── PriceTests.java
            └── infrastructure
                ├── controller
                │   └── PriceControllerTests.java
                ├── mapper
                │   └── PriceMapperTests.java
                └── persistence
                    └── JpaPriceRepositoryTests.java
```

El proyecto utiliza GitHub Actions para automatizar tareas de build, test y cobertura.

```
.github
└── workflows
    ├── build.yml       # Compila el proyecto
    ├── test.yml        # Ejecuta los tests
    └── codecov.yml     # Genera y sube el reporte de cobertura a Codecov
```

Este proyecto incluye un entorno Docker básico para levantar un servidor de SonarQube en local.

```
docker
├── docker-compose.yml     # Define el servicio SonarQube
├── generate-token.sh      # Script para cambiar contraseña y generar un token automáticamente
├── start.sh               # Script para levantar SonarQube con Docker Compose
└── stop.sh                # Script para detener y limpiar SonarQube
```

## Ejecutar la aplicación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/gs-rest-service.git
   ```

   ```bash
   cd gs-rest-service
   ```

2. Asegúrate de tener instalado JDK 21 y que esté configurado en tu entorno:
   ```bash
   java -version
   ```
   Debe mostrar algo como:
   ```
   openjdk version "21"
   ```

3. Ejecuta la aplicación:
   ```bash
   ./gradlew bootRun
   ```

4. Abre Swagger UI en tu navegador para explorar la API: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Ejemplos de respuesta

Respuesta devuelta por la API cuando se encuentra un precio válido para los parámetros indicados.

```json
{
  "id": "22222222-2222-2222-2222-222222222222",
  "brandId": 1,
  "productId": 35455,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "priceList": 2,
  "priority": 1,
  "amount": 25.45,
  "currency": "EUR"
}
```

Este error ocurre cuando un parámetro no tiene el formato esperado, como un número inválido o una fecha que no sigue el formato yyyy-MM-dd'T'HH:mm:ss.

```json
{
  "timestamp": "2025-05-29T12:00:00",
  "status": 400,
  "error": "Invalid parameter type",
  "message": "Invalid value for parameter: 'date'"
}
```

Este error aparece cuando no se encuentra ningún precio aplicable para la marca, producto y fecha indicados.

```json
{
  "timestamp": "2025-05-29T12:30:00",
  "status": 404,
  "error": "Price not found",
  "message": "No price found for productId=99999, brandId=1, date=2020-06-14T10:00"
}
```

## Ejecutar los tests

Ejecuta todos los tests del proyecto con el siguiente comando:

```bash
./gradlew test
```

> `JAVA_HOME` debe apuntar al JDK 21.

## Reporte de cobertura con JaCoCo

Generar el informe de cobertura con el siguiente comando:

```bash
./gradlew test jacocoTestReport
```

> `JAVA_HOME` debe apuntar al JDK 21.

El reporte HTML estará disponible en:
`build/jacocoHtml/index.html`.

## Cómo levantar SonarQube

Desde la carpeta raíz del proyecto:

```bash
cd docker
```

```bash
./start.sh
```

Para detener SonarQube:

```bash
./stop.sh
```

Si no deseas usar `start.sh`, puedes levantar SonarQube directamente con el siguiente comando:

```bash
docker run -d --name sonarqube \
  -p 9000:9000 \
  -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true \
  sonarqube:community
```

Puedes detener y eliminar el contenedor SonarQube con:

```bash
docker rm -f sonarqube
```

Esto levantará el contenedor en segundo plano y expondrá SonarQube en [http://localhost:9000](http://localhost:9000).

## Análisis con SonarQube

Este proyecto está configurado para ejecutar análisis de calidad con SonarQube usando el plugin `org.sonarqube`.

Puedes generar el token de acceso automáticamente ejecutando:

```bash
./generate-token.sh
```

El script espera a que SonarQube esté listo, cambia la contraseña predeterminada del usuario `admin` a `Password123!`, lo cual es obligatorio, y genera un token.

Si prefieres hacerlo manualmente:

1. Abre [http://localhost:9000](http://localhost:9000)
2. Inicia sesión con `admin` / `admin`
3. Cambia la contraseña a `Password123!`, paso obligatorio
4. Ve a *My Account → Security*
5. Escribe `local-token`, haz clic en *Generate* y copia el token

Una vez SonarQube esté corriendo, genera el reporte de cobertura Jacoco (requerido):

```bash
./gradlew test jacocoTestReport
```

> `JAVA_HOME` debe apuntar al JDK 21.

Luego ejecuta la tarea `sonar` con el token generado:

- Linux/macOS:

  ```bash
  SONAR_TOKEN=tu_token ./gradlew sonar
  ```

  > `JAVA_HOME` debe apuntar al JDK 21.

- Windows CMD:

  ```cmd
  set SONAR_TOKEN=tu_token
  ```

  ```cmd
  gradlew sonar
  ```

  > `JAVA_HOME` debe apuntar al JDK 21.

- Windows PowerShell:

  ```powershell
  $env:SONAR_TOKEN="tu_token"
  ```

  ```powershell
  ./gradlew sonar
  ```

  > `JAVA_HOME` debe apuntar al JDK 21.

Sustituye `tu_token` por el token que generaste en SonarQube o con `generate-token.sh`.
