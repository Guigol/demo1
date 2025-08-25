# Étape 1 : construire l'application
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/demo1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java","-jar","app.jar"]
