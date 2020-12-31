# Lana Project

## Introduction
Creation of cart for the problem of  [lana](https://github.com/lana/backend-challenge)

Note the instructions are made for linux system.
## Requirements

To build these project is necessary to have Java 11 and Maven 3 installed.
To run is need Docker 20.10 and Docker-Compose 1.27

## Projects in repository
* lana-server
Service responsible of all operations related to the cart, contains in files the products and promotions available. The service is running in the port `8081` and only can be accessed inside the private network of docker.
The endpoints of these service are protected with basic authentication.

* lana-client
These service is responsible to receive the requests from the client and redirect them to the lana-server.
These service is running in port `8080` and can be accessed by outside by anyone.

## Tests
In the lana-client there is no tests, but in the lana-server there is tests in the part of the cart service.
The tests evaluate the operations of create, get and delete cart, get total value of a cart, add or remove product from the cart and the calculation of promotions with the products in the cart.

## Building and running
In the root of the project run the command `mvn clean install` these will build the projects and create two docker containers, one of each project.

To run the project just go to the root of the same and do `docker-compose up` these will start the two servers of the project and you will be able to see the logs.

## Endpoints
The documentation of the endpoints can be found in the root of the project in the folder `api-documentation` and open the index.html file. The documentation was generated using the yml file in the same folder with [OpenApi3](https://swagger.io/)
To be more easy to use the endpoints a [Postman](https://www.postman.com/) collection can be found in the root of the project

## Libs
| Lib | Usage |
| --- | --- |
| spring-boot-starter-web| Starter for building web, including RESTful, applications using Spring MVC |
| spring-boot-starter-test | Starter for testing Spring Boot applications with libraries including JUnit, Hamcrest and Mockito 
| spring-boot-starter-security | Libs of spring to implement security to the endpoints |
| spring feign | Feign makes writing web service clients easier with pluggable annotation support |
| mapstruct | Code generator that greatly simplifies the implementation of mappings between Java bean types based |
| jackson-databind| High-performance JSON processor (parser, generator) |
| exec-maven-plugin | A Maven plugin for building and pushing Docker images |

## Notes
#### Products available:

|ID|Code         | Name              |  Price|
|---|-------------|------------------|----------------
|1|PEN          | Lana Pen          |   5.00€|
|2|TSHIRT       | Lana T-Shirt      |  20.00€|
|3|MUG          | Lana Coffee Mug   |   7.50€|


#### Promotions available:
|Name|Description|Products|
|------|------------|-----------|
|2x1|Pay 1 and receive other free|PEN|
|More3|Receive 25% off for the buy of 3 items|TSHIRT
