# Stage 1: Build the fatJar
FROM gradle:8.4-jdk17 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN gradle fatJar

# Stage 2: Run the fatJar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
