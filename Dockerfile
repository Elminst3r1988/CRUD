FROM maven:3-openjdk-17

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/CRUD-0.0.1-SNAPSHOT.jar"]