FROM openjdk:17-jdk-alpine
EXPOSE 8191
ARG JAR_FILE=build/libs/hw36-loadbalancer-1.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]