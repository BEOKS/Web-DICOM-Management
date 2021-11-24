FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.knuipalab.dsmp.DsmpApplication"]

# 스프링 코드를 업데이트 하여 도커 이미지를 빌드 및 컨테이너를 실행하는 방법: README.md 참조
# Before build Docker image, you have to update DEPENDENCY info with ```java xf build/libs/DSMP-0.0.1-SNAPSHOT.jar```