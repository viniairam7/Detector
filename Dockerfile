# Etapa 1: Build com Maven
FROM maven:3-jdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
 
# Etapa 2: Runtime com JDK leve
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Este comando copia o JAR que foi gerado
COPY --from=build /app/target/*.jar app.jar 
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "app.jar"]
