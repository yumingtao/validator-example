package com.example.demo.controller;

import com.example.demo.api.dto.IntrospectionResponse;
import com.example.demo.service.HelloService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;


/**
 * @author yumingtao
 */
@RestController
@Slf4j
public class HelloController {
    private HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello/{kid}")
    public String hello(@PathVariable(name = "kid") String pid) {
        log.info("The pid is {}", pid);
        return helloService.sayHello(pid);
    }

    @PostMapping("/hello2")
    public String hello2(@RequestParam(value = "name") String name) {
        return "Hello, " + name;
    }

    @PostMapping("/hello3")
    public String hello3(@RequestParam(value = "name") String name) {
        String result = null;
        try {
            result = helloService.sayHello3(name);
        } catch (FeignException e) {
            log.info("Get exception : {}", e.getMessage());
        }
        return result;
    }

    @PostMapping("/hello4")
    public IntrospectionResponse hello4() {
        IntrospectionResponse response = null;
        try {
            response = helloService.sayHello4();
        } catch (FeignException e) {
            log.info("Get exception : {}", e.getMessage());
            if (e.status() != 0) {
                String content = e.contentUTF8();
                log.info("Exception content : {}", content);
            }
        }
        return response;
    }

    @PostMapping("/hello5")
    public IntrospectionResponse hello5() {
        IntrospectionResponse response = null;
        try {
            response = helloService.sayHello4();
        } catch (Exception e) {
            log.info("Get exception : {}", e.getMessage());
        }
        return response;
    }

    @PostMapping("/hello6")
    public Object hello6() {
        Object result = null;
        try {
            result = helloService.sayHello6();
            log.info("The result : {}", result);
        } catch (Exception e) {
            log.error("Get exception:{}", e.getMessage());
        }
        return result;
    }

    @PostMapping("/hello7")
    public Object hello7() {
        Object result = null;
        try {
            result = helloService.sayHello7();
            log.info("The result : {}", result);
        } catch (URISyntaxException e) {
            log.error("Get exception:{}", e.getMessage());
        }
        return result;
    }

    @PostMapping("/hello8")
    public Object hello8() {
        Object result = null;
        try {
            result = helloService.sayHello8();
            log.info("The result : {}", result);
        } catch (URISyntaxException e) {
            log.error("Get exception:{}", e.getMessage());
        }
        return result;
    }
}