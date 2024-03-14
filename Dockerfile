FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build app/target/listForParty-0.0.1-SNAPSHOT.jar telegram-bot.jar

ENTRYPOINT ["java","-jar","telegram-bot.jar"]