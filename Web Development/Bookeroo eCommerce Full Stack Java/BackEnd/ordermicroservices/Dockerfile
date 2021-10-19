FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} order.jar
EXPOSE 1004
ENTRYPOINT ["java", "-jar", "order.jar"]