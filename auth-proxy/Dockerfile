FROM openjdk:8-jdk-alpine
ADD target/auth-proxy-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app.jar"]
