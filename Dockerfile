# Maven
FROM maven:3.8.1-openjdk-17-slim AS builder
WORKDIR /app
COPY . ./
RUN mvn clean install
USER nonroot

# RTSDK Java
FROM openjdk:17-jre-slim-buster
WORKDIR /app

COPY --from=builder /app/target/splitwise-0.0.1-SNAPSHOT.jar.
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "splitwise-0.0.1-SNAPSHOT.jar"]