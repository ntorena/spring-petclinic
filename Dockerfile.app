# Etapa 1: Construir el proyecto
FROM maven:3.8-openjdk-17 AS builder
# Configurar la memoria máxima para la JVM
ENV JAVA_OPTS="-Xmx2048m -Xms512m"
# Configurar opciones específicas de Maven
ENV MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=256m"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

