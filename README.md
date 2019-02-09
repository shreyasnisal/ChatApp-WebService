# ChatApp-WebService

This repository contains the web-service for the Chat-App. Check the Quiz-App repository for use.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

maven

MySQL

### Installing

Create a MySQL database named *messagesdb*. Create two tables in this database, *users_table* and *messages_table* using the following commands:
`create table users_table(user varchar(100), password varchar(100));`
and
`create table messages_table(message varchar(1000), sender varchar(100), recipient varchar(100));`

In src\main\java\com\app\chatapp\rest, open the RestService.java file and enter your MySQL user and password where mentioned.

Navigate to the *Questions* directory and run `mvn clean install`

Start the web-service using `mvn jetty:run`

## Running the tests

The web-service would be running at the port mentioned in the terminal (default 8093).
To test the web-service, open your browser and use the url:
> localhost:8093/chatapp/isReady

You should get a response *ready*. The web-service is now ready to be used.

## Deployment

### Using the web-service from same machine
To use the web-service from the same machine, use the below url followed by the method to be called:
> localhost:8093/chatapp/

### Using the web-service from a different machine
To use the web-service from a different machine, make sure the client machine and machine running the web-service are on the same network. Then use the below url followed by the method to be called (where server-ip is the ip address of the server machine):
> server-ip:8093/chatapp/

Note: In case of windows user, the firewall on the server machine must be turned off to allow client machines to acceess the web-service.

## Contributing

Issues are welcome. Please add a screenshot of bug and code snippet. Quickest way to solve issue is to reproduce it on one of the examples.

Pull requests are welcome. If you want to make major changes, it is better to first create an issue and discuss it first.

