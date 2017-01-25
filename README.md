# springboot-musicapi
Small project for testing out Spring Boot. It is a webservice which exposes one RESTful resource for getting information about an artist from different sources.
The sources used are Music Brainz API, Wikipedia and Cover Art Archive.

Its dependencies and build functions are configured using Maven.

## Testing
The tests can be run via maven: 
```bash 
mvn test
```

## Building
Maven package creates an executable jar file
```bash 
mvn package
```

# Running
The service is built with Spring Boot and can be run with the Spring Boot Maven plugin:

```bash 
mvn spring-boot:run 
```
An example request:
 ```
 http://localhost:8080/artists/5b11f4ce-a62d-471e-81fc-a69a8278c7da 
 ```

