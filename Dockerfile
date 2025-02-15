# Usar Amazon Corretto 17 como base
FROM amazoncorretto:17

# Definir el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado
COPY target/app-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8080 (el mismo que usa Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación con el perfil "docker"
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
