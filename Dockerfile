FROM openjdk:17-slim
VOLUME /tmp
COPY target/observability-0.8.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
