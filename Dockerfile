# Etapa 1: Build com Maven
 FROM maven:3.9.6-eclipse-temurin-21 AS build
 WORKDIR /app
 COPY . .
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
