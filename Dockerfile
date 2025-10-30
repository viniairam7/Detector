# Etapa 1: Build com Maven
# Alterado para usar a imagem JDK estável e o Maven Wrapper (mvnw)
FROM openjdk:21-jdk-slim AS build 
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
 
# Etapa 2: Runtime com JDK leve
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Este comando copia o JAR que foi gerado
COPY --from=build /app/target/*.jar app.jar 
EXPOSE 8080 # Porta da sua aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
