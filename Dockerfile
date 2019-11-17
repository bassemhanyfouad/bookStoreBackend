FROM java:8
COPY ./target/bookstore.jar /usr/src/bookstore/
WORKDIR /usr/src/bookstore
EXPOSE 8080
CMD ["java", "-jar", "bookstore.jar"]