#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/api-hc-0.0.1-SNAPSHOT.jar api-hc.jar
# ENV PORT=8080
EXPOSE 8082
ENTRYPOINT ["java","-jar","api-hc.jar"]