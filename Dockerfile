FROM amazoncorretto:17 AS builder

WORKDIR /app

COPY . .

# Instala tar + gzip (necesarios para mvnw)
RUN yum update -y && \
    yum install -y tar gzip && \
    yum clean all

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
