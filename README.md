# How Much

A really simple bus fare calculator. Calculates the fare of each passenger from a csv file (`files/in/taps.csv`) and exports the
results to a separate csv file (`files/out/trips.csv`).

## Getting Started

### run via IDE with Spring plugin

1. Import as maven project.
2. Run as Spring Boot application.

### run via IDE without Spring plugin

1. Import as maven project.
2. Run `Application.java`.

### run via command line

```
cd how-much
./mvnw spring-boot:run
```

### run tests via command line

```
cd how-much
./mvnw test
```

## Assumptions

- Rows may not be ordered chronologically.
- A passenger can have multiple destinations in a day.
- Taps file contains multiple passengers.
- A passenger can TAP ON and TAP OFF at the same stop and won't be charged regardless how long they stayed on the bus.

## Tech Stack

- Java 8
- Spring Boot 2.7.3
- Maven
- H2 (in memory db)