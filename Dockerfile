FROM maven:3.6.3-jdk-11 AS builder

ADD ./ /app

WORKDIR /app

RUN mvn clean install

FROM openjdk:11.0.9.1-jre

COPY --from=0 /app/challenge-api/target/challenge-api-*.jar /app/application.jar

WORKDIR /app

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} /app/application.jar