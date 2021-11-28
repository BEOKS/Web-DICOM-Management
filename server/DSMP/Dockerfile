FROM gradle:7.3.0-jdk17-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN gradle wrapper --gradle-version 7.2
RUN ./gradlew bootJar

FROM gradle:7.3.0-jdk17-alpine
COPY --from=builder /app/build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]