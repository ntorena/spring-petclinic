# Usa una imagen base de Maven con JDK 17 para ejecutar pruebas en Java
FROM maven:3.8-openjdk-17

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación a la imagen
COPY target/*.jar app.jar

# Expone el puerto que usa la aplicación
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
