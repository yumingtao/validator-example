# Introduction
1. A simple demo to use multi validator or handler.
Can use simple factory pattern, factory method pattern or abstract factory to do it, but use this is simple and flexible.
2. Use feign client to invoke a spring-security enabled REST api

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