# Build stage
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim
COPY --from=build /target/music_streaming_backend-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "music_streaming_backend.jar"]
