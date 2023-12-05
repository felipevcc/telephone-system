<div align="center">
  <h1>Telephone System</h1>
  <h4>
    <a href="https://github.com/felipevcc/telephone-system#description">Description</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#architecture">Architecture</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#tech-stack">Tech Stack</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#installation">Installation</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#notes">Notes</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#documentation">Documentation</a>
    •
    <a href="https://github.com/felipevcc/telephone-system#author">Author</a>
  </h4>
</div>

## Description

System for assigning telephone numbers to clients of the Calitel company. The system allows you to assign telephone numbers to both residential and commercial clients, by geographical areas, which are neighborhoods in the city of Cali. These number assignments are made according to the availability of telephone exchanges for said areas.

## Architecture

<img src="https://i.imgur.com/UBijuGk.jpg" alt="diagram">

## Tech Stack

### Database

- Engine: Oracle 21c XE (Express Edition)
- Command line tool: SQLPlus 21.3.0

### Backend

- Java - [JDK 11](https://www.oracle.com/co/java/technologies/javase/jdk11-archive-downloads.html)
- Spring Boot 2.7.17
- Gradle 8.3
- IDE: IntelliJ IDEA Community Edition 2023.2.2

### Frontend

- Node ^18.10.0
- Angular 15.2.0

## Installation

### Database

Follow the instructions in the [database configuration guide](https://github.com/felipevcc/telephone-system/tree/main/DB#readme).

### Backend

First, from the editor (IntelliJ IDEA) open each of the services in the API/ directory. The editor will start configuring and installing the projects.

Once configured, run the services from the editor in this order:

1. [eureka-server](https://github.com/felipevcc/telephone-system/tree/main/API/eureka-server)
2. [area-service](https://github.com/felipevcc/telephone-system/tree/main/API/area-service)
3. [center-service](https://github.com/felipevcc/telephone-system/tree/main/API/center-service)
4. [customer-service](https://github.com/felipevcc/telephone-system/tree/main/API/customer-service)
5. [telephone-number-service](https://github.com/felipevcc/telephone-system/tree/main/API/telephone-number-service)
6. [api-gateway](https://github.com/felipevcc/telephone-system/tree/main/API/api-gateway)

### Frontend

Locate yourself in the directory Front/calitel-front/

Once inside, run the following command to install the dependencies:

```bash	
npm install
```

Once the dependencies are installed, run the following command to start the application:

```bash
ng serve --open
```

## Notes

- To assign telephone numbers to residential customers, numbers related to the customer's name are assigned.
The telephone number must have from the second digit the numbers related to the first four letters of the name, following the rule of these numbers associated with letters: <img src="https://i.imgur.com/7hQmO0C.png" alt="letters" height="250">

- The center associated with the geographical areas (neighborhoods) are already loaded in the database, with sufficient availability to assign numbers. Likewise, the creation of centers can be done through the system but with a minimum range of 10 million, to have sufficient availability of numbers and to be able to comply with the rule of telephone numbers for residential customers.

- Redux is used with ngrx in the frontend to have a system state, where records that are frequently used are loaded, such as geographical areas, types of clients (residential, commercial) and document types ( CC, CE, PA).

- In the [annexes directory](https://github.com/felipevcc/telephone-system/tree/main/Docs/anexos) is the [postman collection](https://github.com/felipevcc/telephone-system/blob/main/Docs/anexos/TC_GB_09.postman_collection.json) with all the API endpoints to make the requests.

- In the [annexes directory](https://github.com/felipevcc/telephone-system/tree/main/Docs/anexos) there is a [csv file](https://github.com/felipevcc/telephone-system/blob/main/Docs/anexos/MOCK_DATA.csv) with 1000 sample client records generated, to show how to load clients into the system and the format they should take.

## Documentation

The documentation of the API endpoints was done using Swagger. The documentation for all microservices is in the [swagger directory](https://github.com/felipevcc/telephone-system/tree/main/Docs/swagger).

## Author

* Felipe Villamizar <a href="https://github.com/felipevcc" rel="nofollow"><img align="center" alt="github" src="https://www.vectorlogo.zone/logos/github/github-tile.svg" height="24" /></a>
