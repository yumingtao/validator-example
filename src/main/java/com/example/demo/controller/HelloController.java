package com.example.demo.controller;

import com.example.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public String hello2(@RequestParam String name) {
        return "Hello, " + name;
    }

    @PostMapping("/hello3")
    public String hello3(@RequestParam String name) {
        return helloService.sayHello3(name);
    }
}
