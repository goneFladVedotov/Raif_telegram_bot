FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:19.0.2
WORKDIR /app
COPY --from=builder /app/target/*.jar application.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "application.jar"]