FROM maven:3.8.1-jdk-11-slim AS build

COPY ./ ./

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "target/produto-ms-0.0.1-SNAPSHOT.jar"]