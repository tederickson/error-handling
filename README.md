# LPL Common REST Classes

Demonstrate Spring Boot REST application error handling.

The basic idea is the @ControllerAdvice classes convert an Exception to a REST response.

The driving force for this project is https://lplfinancial.atlassian.net/wiki/spaces/FAN/pages/58909262301/Spike+Refining+API+Responses 

## Helpful links
* https://spring.io/guides/tutorials/rest
* https://zetcode.com/springboot/controlleradvice/
* https://www.baeldung.com/spring-valid-vs-validated


## Build
In order to build locally, setup `~/.gradle/gradle.properties` with the following properties to authenticate
with the [internal Maven repository on Artifactory](https://lplfinancial.atlassian.net/wiki/spaces/DEV/pages/58605045930/Maven+onboarding)

```
mavenUser=<CORP username>
mavenPassword=<CORP password>
```

## Test
com/lpl/rest/common/controller/CityControllerTest.java uses MockMvc to test the endpoints.

## Demo Results
* Start the server by using `gradle bootRun`
* Use Postman or the provided curl commands.

### Service throws LplNoDataFoundException()
`curl -v --location --request GET 'localhost:8080/cities'`
```json
{
    "description": "No data found",
    "field": null,
    "value": null,
    "error_code": 15
}
```

### Service throws LplCityNotFoundException(id)
`curl -v --location --request GET 'localhost:8080/cities/23'`
```json
{
    "description": "City with Id 23 not found",
    "field": null,
    "value": null,
    "error_code": 111
}
```

### "/cities/{id}" with invalid id
`curl -v --location --request GET 'localhost:8080/cities/apple'`
```json
{
    "timestamp": "2021-08-14T17:53:56.471+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/cities/apple"
}
```
The ControllerAdvisor was not called.  The message comes from Spring.

### Null Pointer Exception
`curl -v --location --request GET 'localhost:8080/bad'`
```json
{
    "description": "demo handling Java exceptions",
    "field": null,
    "value": null,
    "error_code": null
}
```
Log shows
```bash
2021-08-14 11:55:48.634 ERROR 11196 --- [nio-8080-exec-8] c.l.r.c.t.GenericAdvisor                 : Shame, shame, shame

java.lang.NullPointerException: demo handling Java exceptions
        at com.lpl.rest.common.controller.CityController.demoGenericHandler(CityController.java:47) ~[main/:?]
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:?]
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:?]
        at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:?]
        at java.lang.reflect.Method.invoke(Method.java:566) ~[?:?]

```

### Create City missing name
```
curl -v --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "population": 15923
}'
```
```json
{
    "description": "Null parameter",
    "field": "name",
    "value": null,
    "error_code": 11234
}
```
### Create City missing population
```
curl -v --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City"
}'
```
```json
{
    "description": "Population can not be less than 1",
    "field": "population",
    "value": "0",
    "error_code": 11234
}
```

### Create City with ID
```json
curl -v --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City",
    "population": 15923,
    "id":12
}'
```
```json
{
    "description": "ID not part of save, use update instead",
    "field": "id",
    "value": "12",
    "error_code": 11234
}
```
### Update City missing ID
```
curl -v --location --request PUT 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City",
    "population": 15923
}'
```
```json
{
    "description": "Null parameter",
    "field": "id",
    "value": null,
    "error_code": 11234
}
```