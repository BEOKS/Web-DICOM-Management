FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=build/libs
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.knuipalab.dsmp.DsmpApplication"]

# Before build Docker image, you have to update DEPENDENCY info with ```java xf build/libs/DSMP-0.0.1-SNAPSHOT.jar```