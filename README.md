# Broadcast

# API

The back was generated with [Java 17.0.7](https://www.oracle.com/java/technologies/javase/17-0-7-relnotes.html) and [Spring Boot 3.1.2](https://spring.io/blog/2023/07/20/spring-boot-3-1-2-available-now)

1 - Install java 17.0.7

2 - Clone this project on your local

3 - Inside folder project (api) `$ cd <PATH_REPO>/broadcast/api` exec:

    -  `mvn dependency:tree` # get dependency 
    -  `mvn spring-boot:run` # start project 


After mvn spring-boot:run the api will start on http://localhost:3001.

# front

1- Clone this project on your local

2- $ Install a simple static HTTP server `$ npm install --global http-server` https://www.npmjs.com/package/http-server

3- Inside folder project (front) `$ cd <PATH_REPO>/broadcast/front` exec : 

> $ http-server -a 127.0.0.1 -p 8089

4 - After the start api and front you can visit : http://127.0.0.1:8089/



