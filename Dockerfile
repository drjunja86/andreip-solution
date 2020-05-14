FROM openjdk:14-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD app/target/app-1.0.0.jar /app/app.jar
ADD generator/target/generator-1.0.0.jar /app/generator.jar
ADD generator/target/postgresql-42.2.12.jar /app/postgresql-42.2.12.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]