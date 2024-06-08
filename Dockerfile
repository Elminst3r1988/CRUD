FROM maven:3-openjdk-17

WORKDIR /app

COPY pom.xml .

RUN mvn package

ENTRYPOINT ["java", "-jar", "target/CRUD-0.0.1-SNAPSHOT.jar"]
