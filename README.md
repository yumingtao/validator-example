# Introduction
1. A simple demo to use multi validator or handler.
Can use simple factory pattern, factory method pattern or abstract factory to do it, but use this is simple and flexible.
2. Use feign client 
    - Invoke a spring-security enabled REST api, see ```HelloService.sayHello3()```
    - If the third-party REST api has a custom response when the response code is not 200
        - if the status is not 0, use e.contentUTF8() to get the custom response as String, see ```HelloController.hello4()```
        - Create a custom error decoder to handle all, see ```HelloController.hello5()``` and ```config/HelloErrorDecoder```
3. Use RestTemplate, if the response status code is not 200, it is handled by DefaultResponseErrorHandler by default, it throws the exception directly and can't show the error response details. 
    - Solution 1: Catch HttpClientErrorException and get the response body, see ```HelloService.sayHello6()```
    - Solution 2: Use custom error handler HelloErrorHandler implementing ResponseErrorHandler, see ```HelloService.sayHello7()```
    - Solution 2: Use custom error handler HelloDefaultErrorHandler override handleError method DefaultResponseErrorHandler, see ```HelloService.sayHello8()```
    
    - Note:
        - The RequestFactory should use HttpComponentsClientHttpRequestFactory, if use SimpleClientHttpRequestFactory, the response body is null
        - Need to add Apache HttpClient dependency in pom if use HttpComponentsClientHttpRequestFactory

# Test and run
- Start the project
    ```shell script
    mvn spring-boot:run
    ```
- Test 'hello'
    > Other token
    ```shell script
    curl -X GET localhost:18888/hello/other
    ```
    - The test result:
    ```shell script
    Other token verify.
    ```
    > Keycloak token
    ```shell script
    curl -X GET localhost:18888/hello/key
    ```
    - The test result:
    ```shell script
    Keycloak token verify.
    ```
- Test 'hello2'
    ```shell script
    curl -X POST -d name=yumingtao localhost:18888/hello2
    ```
    - The test result:
    ```shell script
    Hello, yumingtao
    ```

- Test 'hello3'
    - Start the 'spring-security-example' first
    ```shell script
    mvn spring-boot:run
    ```
    - Test 'hello3' in 'validator-example'
    ```shell script
    curl -X POST -d name=tony localhost:18888/hello3
    ```
    - The test result
    ```shell script
    Hello, tony
    ```

- Test 'hello4'
    - Comment "@Configuration" in HelloErrorDecoder
    - Test 'hello4' in 'validator-example'
    ```shell script
    curl -X POST -d name=tony localhost:18888/hello4
    ```

- Test 'hello5'
    - Test 'hello5' in 'validator-example'
    ```shell script
    curl -X POST -d name=tony localhost:18888/hello5
    ```

- Test 'hello6'
    - Test 'hello6' in 'validator-example'
    ```shell script
    curl -X POST -d name=tony localhost:18888/hello6
    ```

- Test 'hello7'
    - Test 'hello7' in 'validator-example'
    ```shell script
    curl -X POST -d name=tony localhost:18888/hello7
    ```