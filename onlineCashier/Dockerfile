FROM amazoncorretto:19.0.2
WORKDIR /app
COPY build.gradle.kts /app/build.gradle.kts
COPY settings.gradle.kts /app/settings.gradle.kts
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY gradlew.bat /app/gradlew.bat
COPY src /app/src
EXPOSE 8089
RUN ./gradlew
ENTRYPOINT ["./gradlew", "bootRun"]