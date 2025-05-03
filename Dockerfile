# Вибір образу з OpenJDK
FROM openjdk:21-jdk-slim as builder

# Копіюємо JAR файл в контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Вказуємо команду для запуску додатку
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]

# Оголошуємо порт для доступу до програми
EXPOSE 8080
