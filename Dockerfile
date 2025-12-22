# Этап 1: Сборка
FROM maven:3.9.11-eclipse-temurin-25-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Запуск
FROM eclipse-temurin:25-jre-alpine-3.23
WORKDIR /app

# Копируем только JAR из этапа сборки
COPY --from=build /app/target/LMSProject-0.0.1-SNAPSHOT.jar app.jar
RUN ls -la /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]