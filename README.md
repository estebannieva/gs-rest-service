# Inditex API

Este proyecto es una API REST desarrollada con Spring Boot 3 y Java 21 para consultar el precio aplicable a un producto en función de la fecha, el producto y la marca.

## Índice

- [Características](#características)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Ejecutar la aplicación](#ejecutar-la-aplicación)
- [Respuesta de ejemplo](#respuesta-de-ejemplo)
- [Ejecutar los tests](#ejecutar-los-tests)
- [Reporte de cobertura con JaCoCo](#reporte-de-cobertura-con-jacoco)
- [Cómo levantar SonarQube](#cómo-levantar-sonarqube)
- [Análisis con SonarQube](#análisis-con-sonarqube)

## Características

- Arquitectura en capas siguiendo el patrón MVC, con una separación clara entre `config`, `controller`, `service`, `repository`, `dto`, `mapper`, `exception` y `model`.
- Acceso a datos con Spring Data JPA.
- Base de datos H2 embebida (modo en memoria).
- Gestión de excepciones centralizada con `@ControllerAdvice`.
- Mapeo entre entidad y DTO con MapStruct.
- Uso de Lombok para reducir el código boilerplate.
- Tests de integración para `PriceController`, repositorio, mapeo y manejo de excepciones.
- Flujo de trabajo con GitHub Flow para gestionar ramas y cambios.

## Estructura del proyecto

```
com.example.restservice
├── config              # Configuración general (OpenAPI, etc.)
├── controller          # Controlador REST
├── dto                 # Objetos de transferencia de datos
├── exception           # Excepciones personalizadas y handler global
├── mapper              # Interfaces de mapeo DTO <-> Entidad
├── model               # Entidad JPA (Price)
├── repository          # Repositorio Spring Data
├── service             # Lógica de negocio (interface + implementación)
└── RestServiceApplication.java
```

Este proyecto incluye un entorno Docker básico para levantar un servidor de SonarQube en local.

```
docker/
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

## Respuesta de ejemplo

Respuesta devuelta por la API cuando se encuentra un precio válido para los parámetros indicados.

```json
{
  "id": "11111111-1111-1111-1111-111111111111",
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "priority": 0,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.5,
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
  sonarqube:community
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
