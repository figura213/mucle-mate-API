# Этап сборки
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Кэшируем зависимости Maven
RUN mkdir -p /app/.m2
ENV MAVEN_CONFIG=/app/.m2

# Копируем все файлы проекта
COPY . .

# Устанавливаем права и собираем проект (без тестов)
RUN chmod +x ./mvnw && \
    ./mvnw -B -DskipTests clean install

# Этап рантайма (минимальный образ)
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Копируем скомпилированный jar из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar

# Открываем порт (например, 8080)
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
