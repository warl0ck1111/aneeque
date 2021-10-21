FROM openjdk:8-jdk-alpine
EXPOSE 8787
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-mgnt-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/user-mgnt-0.0.1-SNAPSHOT.jar"]