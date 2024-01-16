FROM openjdk:17-jdk-alpine
EXPOSE 8189
ARG JAR_FILE=build/libs/hw30-innerservice-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]