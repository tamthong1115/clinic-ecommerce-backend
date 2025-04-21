FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

ARG CONFIG_SERVER_URL

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn install -DskipTests


FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=builder ./app/target/*.jar ./app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]
