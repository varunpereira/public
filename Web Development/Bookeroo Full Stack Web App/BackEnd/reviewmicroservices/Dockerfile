FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} review.jar
EXPOSE 1003
ENTRYPOINT ["java", "-jar", "review.jar"]