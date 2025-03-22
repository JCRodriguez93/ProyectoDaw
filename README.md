# Proyecto para el curso 2024 - 2025 "ProyectoDaw" sobre una tienda de tatuajes.

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg)
![HTML5](https://img.shields.io/badge/HTML5-E34F26.svg?logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6.svg?logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E.svg?logo=javascript&logoColor=black)
![License](https://img.shields.io/badge/license-MIT-blue.svg)


## Descripción

Este proyecto es un backend desarrollado en IntelliJ IDEA para una tienda de productos de tatuajes. Su propósito es gestionar usuarios, productos y pedidos de manera eficiente, permitiendo una experiencia de compra fluida.

La plataforma admite registro, inicio y cierre de sesión de usuarios, gestión de roles con distintos niveles de acceso y funcionalidades para la administración de productos clasificados en categorías y subcategorías. Además, cada usuario dispone de un carrito de compras personal y la posibilidad de realizar pedidos.

## Características

- **Gestión de Usuarios**: Registro, autenticación segura y asignación de roles.
- **Gestión de Productos**: Creación, edición y eliminación de artículos organizados en categorías y subcategorías.
- **Carrito de Compras**: Permite a los usuarios añadir productos, modificar cantidades y proceder con la compra.
- **Gestión de Pedidos**: Creación, actualización y seguimiento de órdenes de compra.
- **Sistema de Roles**: Diferentes niveles de acceso para usuarios y administradores.

## Dependencias

```xml
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.2.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>javax.annotation</groupId>
			<artifactId>javax.annotation-api</artifactId>
			<version>1.3.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.2</version>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.2</version>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.11.2</version>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct</artifactId>
			<version>1.5.2.Final</version>
		</dependency>

		<dependency>
			<groupId>org.mapstruct</groupId>
			<artifactId>mapstruct-processor</artifactId>
			<version>1.5.2.Final</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>javax.validation</groupId>
			<artifactId>validation-api</artifactId>
			<version>2.0.1.Final</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.12.6</version>
		</dependency>

		<dependency>
			<groupId>jakarta.persistence</groupId>
			<artifactId>jakarta.persistence-api</artifactId>
			<version>3.1.0</version>
		</dependency>

		<dependency>
			<groupId>io.swagger.core.v3</groupId>
			<artifactId>swagger-annotations</artifactId>
			<version>2.0.9</version>
		</dependency>

		<dependency>
			<groupId>com.github.joschi.jackson</groupId>
			<artifactId>jackson-datatype-threetenbp</artifactId>
			<version>2.15.2</version>
		</dependency>
```

## Configuración de Swagger Codegen.

Permite generar API's y Modelos en base al fichero en  `/contract/api.yaml`. Se fixea también la generación de formato fecha a `LocalDateTime` de Java.

```xml
<plugin>
				<groupId>io.swagger.codegen.v3</groupId>
				<artifactId>swagger-codegen-maven-plugin</artifactId>
				<version>3.0.36</version>
				<executions>
					<execution>
						<goals>
							<goal>generate</goal>
						</goals>
						<configuration>
							<inputSpec>${project.basedir}/src/main/resources/contract/api.yaml</inputSpec>
							<language>spring</language>
							<output>${project.build.directory}/generated-sources/</output>
							<configOptions>
								<apiPackage>es.swagger.codegen.api</apiPackage>
								<modelPackage>es.swagger.codegen.models</modelPackage>
								<interfaceOnly>true</interfaceOnly>
								<dateLibrary>custom</dateLibrary>
							</configOptions>
							<typeMappings>
								<typeMapping>DateTime=java.time.LocalDateTime</typeMapping>
							</typeMappings>
							<importMappings>
								<importMapping>LocalDateTime=java.time.LocalDateTime</importMapping>
							</importMappings>
						</configuration>
					</execution>
				</executions>
			</plugin>
```

# Endpoints para la gestión del microservicio
<details>
<summary>Endpoint: /auth/register</summary>

### Método: `POST`.

### Descripción:
Este endpoint se utiliza para registrar un nuevo usuario en el sistema.

### Etiquetas:
- **Authentication**

### Operación:
- **operationId**: `registerUser`

### Cuerpo de la solicitud:
- **Descripción**: Datos necesarios para registrar un usuario.
- **Requerido**: Sí
- **Tipo de contenido**: `application/json`
- **Esquema**: `RegisterRequest` (definido en los componentes del esquema)

### Respuestas:
- **201**: Usuario registrado exitosamente.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **400**: Datos inválidos (por ejemplo, falta algún campo obligatorio).
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **409**: Conflicto (por ejemplo, el email ya está en uso).
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **500**: Error interno del servidor.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
</details>

<details>
<summary>Endpoint: /auth/login</summary>

### Método: `POST`.

### Descripción:
Este endpoint se utiliza para iniciar sesión en el sistema.

### Etiquetas:
- **Authentication**

### Operación:
- **operationId**: `loginUser`

### Cuerpo de la solicitud:
- **Descripción**: Credenciales necesarias para autenticar al usuario.
- **Requerido**: Sí
- **Tipo de contenido**: `application/json`
- **Esquema**: `LoginRequest` (definido en los componentes del esquema)

### Respuestas:
- **200**: Inicio de sesión exitoso.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **400**: Datos inválidos (credenciales faltantes o formato incorrecto).
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **401**: Credenciales incorrectas.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **403**: Acceso denegado (usuario bloqueado, no activado, etc.).
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)
- **500**: Error interno del servidor.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `AuthResponse` (definido en los componentes del esquema)

</details>



<details>
<summary>Endpoint: /auth/logout</summary>

### Método: `POST`.

### Descripción:
Este endpoint se utiliza para cerrar sesión en el sistema.

### Etiquetas:
- **Authentication**

### Operación:
- **operationId**: `logoutUser`

### Respuestas:
- **200**: Sesión cerrada exitosamente.
  - **Tipo de contenido**: `application/json`
  - **Esquema**: `LogoutResponse` (definido en los componentes del esquema)
 
</details>
## (he de continuar con la descripción).
