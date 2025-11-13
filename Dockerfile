# =========================
# Stage 1: Build the project
# =========================
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /usr/src/app
COPY . .
RUN mvn clean package -DskipTests

# =========================
# Stage 2: Run the application
# =========================
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /usr/src/app/target/erb_web_be-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
