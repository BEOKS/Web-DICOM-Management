FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/dsmp-docker .