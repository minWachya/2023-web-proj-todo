package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    // 1. /test
    @GetMapping // Get 요청 시 동작
    public String testController() {
        return "Hello, World!";
     }

    // 2. test/testGetMapping
    @GetMapping("/testGetMapping")
    public String testControllerGetMapping() {
        return "Hello, World!";
    }

    // 3. test/{id}
    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello, World! id: " + id;
    }

}
