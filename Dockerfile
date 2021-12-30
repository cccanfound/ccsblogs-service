FROM openjdk:8
MAINTAINER cc
COPY target/ccwordhelper-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8060
WORKDIR /app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/conf/application.properties"]