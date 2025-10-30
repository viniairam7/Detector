# Etapa 1: Build com Maven
 FROM openjdk:21-jdk-slim AS build
 WORKDIR /app
 COPY pom.xml .
 RUN mvn dependency:go-offline
 COPY src ./src
 RUN mvn clean package -DskipTests
 
 # Etapa 2: Runtime com JDK leve
 FROM eclipse-temurin:21-jdk-alpine
 WORKDIR /app
 COPY --from=build /app/target/*.jar app.jar
 EXPOSE 3306
 ENTRYPOINT ["java", "-jar", "app.jar"]

ENV SPRING_DATASOURCE_URL=jdbc:mysql://[HOST_DO_SEU_MYSQL]:3306/fraud_db?useSSL=false
ENV SPRING_DATASOURCE_USERNAME=fraud_app_user
ENV SPRING_DATASOURCE_PASSWORD=FraudeSDM
