FROM openjdk

WORKDIR /app

COPY target/produto-ms-0.0.1-SNAPSHOT.jar /app/produto-ms.jar

ENTRYPOINT ["java", "-jar", "produto-ms.jar"]

EXPOSE 9999