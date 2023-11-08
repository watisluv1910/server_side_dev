FROM maven:3.9.4-amazoncorretto-20-debian as build

WORKDIR /app-server

# Copy the pom.xml file
COPY pom.xml .
COPY src src

RUN mvn clean package

FROM amazoncorretto:20-alpine-jdk

COPY --from=build /app-server/target/*.jar app.jar

CMD ["java -jar app.jar"]
