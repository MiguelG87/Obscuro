FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/obscuro-0.0.1-SNAPSHOT.jar ./obscuro.jar
EXPOSE 8080
CMD ["java", "-jar", "obscuro.jar"]