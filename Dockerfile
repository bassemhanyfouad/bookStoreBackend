FROM java:8
COPY ./target/ /usr/src/bookstore/
WORKDIR /usr/src/bookstore
CMD ["java", "-jar", "bookstore.jar"]