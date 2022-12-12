FROM openjdk:11-slim as builder
WORKDIR application
ADD target/archademiks-notification-service-0.0.1-SNAPSHOT.jar archademiks-notification-service-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar archademiks-notification-service-0.0.1-SNAPSHOT.jar extract

FROM openjdk:11-slim
LABEL PROJECT_NAME=archademiks-notification-service PROJECT=0.0.1-SNAPSHOT.jar
EXPOSE 8083
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/snapshot-dependencies ./
COPY --from=builder application/application ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/ ./urandom", "org.springframework.boot.loader.JarLauncher"]