FROM java:8
MAINTAINER parthippkn@gmail.com
## assignment right to left
EXPOSE 9080:8080
ENV LOG_FILE_NAME /app/logs/app.log
COPY spring-boot-docker-0.0.1-SNAPSHOT.jar /app/app.jar
VOLUME /app/logs
ENTRYPOINT ["java", "-jar", "/app/app.jar"]