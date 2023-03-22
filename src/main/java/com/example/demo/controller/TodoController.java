package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TodoController {
    // 자동 주입
    @Autowired
    private TodoService service;

    @GetMapping("test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    /*
    과제:
     메핑 URL: localhost:8080/todo/testTodo
     반환 값: (TodoDTO 객체와 status: 200)
        반환할 TodoDTO 객체: { “id”:”123”, “title”:”[본인이름]”, “done”: true }
    */
    @GetMapping("/testTodo")
    public ResponseEntity<TodoDTO> todoControllerResponseEntity() {
        TodoDTO response = TodoDTO.builder().id("123").title("이민영").done(true).build();
        return ResponseEntity.ok().body(response);
    }
}
