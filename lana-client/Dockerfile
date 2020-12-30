FROM openjdk:11.0.9.1-jre-buster
VOLUME /tmp
EXPOSE 8081
ADD target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar"]
