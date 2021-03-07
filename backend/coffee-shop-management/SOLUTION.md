# CoffeShop Management inAtlas Challenge

## Introduction

I've finished the challenge wit my own solution to all the bugs/features proposed in it. 
I completed the bug fixes and I implemented a Spring boot application to expose several endpoint of a RESTFul API 
to manage the CoffeeShop. 

## Compilation instructions

You must use Maven to compile the project by this execution command:
* mvn compile

Before create the jar file, you can execute the tests by this command:
* mvn test

In order to create a jar file of the application you must execute:
* mvn package

This command creates a jar file into target directory.

## Application Execution

You can execute the application running the jar file:
* java -jar target/inatlas-challenge-0.1.0-SNAPSHOT.jar
This execute a Spring Boot that execute a web application in 8080 port

You can execute a dockerize application version with the following commands:
* Create the image: docker build -t enriquedelacruz/inatlaschallenge .
* Execute the container: docker run -d -p 8080:8080 enriquedelacruz/inatlaschallenge

In addition, you can run several container for every coffeeshop of the company.
You must change the host port to run other isolated JVM for each coffeeShop:
* docker run -d -p 8081:8080 enriquedelacruz/inatlaschallenge
* docker run -d -p 8082:8080 enriquedelacruz/inatlaschallenge
* docker run -d -p 8083:8080 enriquedelacruz/inatlaschallenge
* ...