# Этап сборки
FROM openjdk:21-jdk-slim as builder

WORKDIR /app

# Копируем JAR файл из target
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Финальный образ
FROM openjdk:21-jdk-slim

WORKDIR /app

# Копируем JAR файл из предыдущего этапа
COPY --from=builder /app/demo.jar /app/demo.jar

# Открываем порт 8080 для доступа
EXPOSE 8080

# Команда для запуска приложенияв
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
