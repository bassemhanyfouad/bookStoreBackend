FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
RUN mkdir -p /bookstore/
RUN mkdir -p /bookstore/logs/
ADD target/bookstore.jar /bookstore/bookstore.jar
ENTRYPOINT ["java","-Djava.secumrity.egd=file:/dev/./urandom","-Dspring.profiles.active=flyway,local", "-jar", "/bookstore/bookstore.jar"]