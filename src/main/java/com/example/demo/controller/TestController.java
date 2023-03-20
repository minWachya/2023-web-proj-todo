package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    // @PathVariable
    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello, World! id: " + id;
    }

    // 4. test/testRequestParam?id={id}
    // @RequestParam
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello, World! ID: " + id;
    }

    // 5. test/testRequestParam2?=body~~ // postman에서 확인
    @GetMapping("/testRequestParam2")
    public String testControllerRequestParam2(@RequestBody(required = false) TestRequestBodyDTO request) {
        return "Hello, World! ID: " + request.getId() + " message: " + request.getMessage();
    }

    // 6. test/testResponseBody
    // 객체 리턴
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello, World! I'm ResponseDTO");
        list.add("민영");
        list.add("안뇽");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }
}
