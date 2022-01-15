FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ARG PROFILE
ENV PROFILE $PROFILE
RUN echo -Dspring.profiles.active=$PROFILE
ENTRYPOINT java -cp app:app/lib/* com.knuipalab.dsmp.DsmpApplication --spring.profiles.active=$PROFILE

# ARG PROFILE
# RUN echo -Dspring.profiles.active=$PROFILE
# # ENTRYPOINT ["echo","$PROFILE","&&","java","-cp","app:app/lib/*","com.knuipalab.dsmp.DsmpApplication","-Dspring.profiles.active=$PROFILE"]
# ENTRYPOINT java -cp app:app/lib/* com.knuipalab.dsmp.DsmpApplication --spring.profiles.active=local
# # ENTRYPOINT java -jar -Dspring.profiles.active=prod app.jar