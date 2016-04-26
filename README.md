# krest
An example of using Kotlin with Spring boot to create a simple RESTful API. The API handles blog post resources, that allows 
creating/reading/updating/deleting simple blog posts.

[![Build Status](https://travis-ci.org/cdowney/krest.svg?branch=master)](https://travis-ci.org/cdowney/krest)

# Building
To build run `./gradelw clean build`

To build an executable jar `./gradlew clean bootRepackage`

# Running
To run from gradle `./gradlew bootRun`

To run the executable jar from `build/libs` run `java -jar krest-0.1.0-SNAPSHOT.jar`

### Spring Profile
To set the active profile set the environment variable `SPRING_PROFILES_ACTIVE` to one of `dev|test|prod`.

# Springfox Swagger UI
The project is configured to package [Springfox Swagger UI](https://github.com/springfox/springfox).

To exercise the API run the application and browse to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

# H2 Database
The `dev` and `test` profiles are configured to use an H2 in memory database with JPA entity mapping. The H2 database can be inspected
when the application is running by using the H2 console. It is configured to be served from [http://localhost:8080/console](http://localhost:8080/console)
To login in to the H2 console select the `Generic H2 (Embedded)` connectionn type and click connect.
