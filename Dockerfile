FROM openjdk:17-slim
VOLUME /tmp
COPY target/observability-0.0.2-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
