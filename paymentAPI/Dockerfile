FROM amazoncorretto:19.0.2
COPY /target/*.jar application.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "application.jar"]