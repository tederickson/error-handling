# Common REST Classes

Demonstrate Spring Boot REST application error handling.

The basic idea is the @ControllerAdvice classes convert an Exception to a REST response.


## Helpful links
* https://spring.io/guides/tutorials/rest
* https://zetcode.com/springboot/controlleradvice/
* https://www.baeldung.com/spring-valid-vs-validated


## Test
com/My/rest/common/controller/CityControllerTest.java uses MockMvc to test the endpoints.

## Demo Results
* Compile the server by using `mvn install`
* Start the server by using `mvn spring-boot:run`
* Use Postman or the provided curl commands.

### Service throws MyNoDataFoundException()
`curl -v --location --request GET 'localhost:8080/cities'`
```json
{
  "status": 404,
  "code": 15,
  "statusMessage": "No data found"
}
```

### Service throws MyCityNotFoundException(id)
`curl -v --location --request GET 'localhost:8080/cities/23'`
```json
{
  "status": 404,
  "code": 111,
  "statusMessage": "City with Id 23 not found"
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
  "status": 500,
  "code": 500,
  "statusMessage": "demo handling Java exceptions"
}
```
Log shows
```bash
2021-08-14 11:55:48.634 ERROR 11196 --- [nio-8080-exec-8] c.l.r.c.t.GenericAdvisor                 : Create high priority Story to fix this

java.lang.NullPointerException: demo handling Java exceptions
        at com.My.rest.common.controller.CityController.demoGenericHandler(CityController.java:47) ~[main/:?]
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:?]
        at jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:?]
        at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:?]
        at java.lang.reflect.Method.invoke(Method.java:566) ~[?:?]

```

### Create City missing name
```bash
curl -v --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "population": 15923
}'
```
```json
{
  "status": 400,
  "code": 11234,
  "statusMessage": "Null parameter",
  "field": "name",
  "value": null
}
```
### Create City missing population
```bash
curl -v --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City"
}'
```
```json
{
  "status": 400,
  "code": 11234,
  "statusMessage": "Population can not be less than 1",
  "field": "population",
  "value": "0"
}
```

### Create City with ID
```bash
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
  "status": 400,
  "code": 11234,
  "statusMessage": "ID not part of save, use update instead",
  "field": "id",
  "value": "12"
}
```
### Update City missing ID
```bash
curl -v --location --request PUT 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City",
    "population": 15923
}'
```
```json
{
    "status": 400,
    "code": 11234,
    "statusMessage": "Null parameter",
    "field": "id",
    "value": null
}
```
### Create a City
```bash
curl --location --request POST 'localhost:8080/cities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "New City",
    "population": 15923
}'
```
```json
{
    "id": 1,
    "name": "New City",
    "population": 15923
}
```
### Get All Cities
`curl -v --location --request GET 'localhost:8080/cities'`
```json
[
    {
        "id": 1,
        "name": "New City",
        "population": 15923
    }
]
```
