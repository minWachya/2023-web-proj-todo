package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";

            // dto로 entity 생성
            TodoEntity entity = TodoDTO.toEntity(dto);
            // id null처리
            entity.setId(null);
            // userid에 임시 id 저장
            entity.setUserId(temporaryUserId);
            // entity 저장 후 userid가 생성한 todo entity 목록 받아옴
            List<TodoEntity> entities = service.create(entity);
            // todo entity 목록을 todo dto 목록으로 변경
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // response 만들어서 출력
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";
        // dto를 entity로 변경
        TodoEntity entity = TodoDTO.toEntity(dto);
        // userid에 임시 id 저장
        entity.setUserId(temporaryUserId);
        // entity 수정 후 userid가 생성한 todo entity 목록 받아옴
        List<TodoEntity> entities = service.update(entity);
        // todo entity 목록을 todo dto 목록으로 변경
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";

            // dto를 entity로 변경
            TodoEntity entity = TodoDTO.toEntity(dto);
            // userid에 임시 id 저장
            entity.setUserId(temporaryUserId);
            // entity 삭제 후 userid가 생성한 todo entity 목록 받아옴
            List<TodoEntity> entities = service.delete(entity);
            // todo entity 목록을 todo dto 목록으로 변경
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // response 만들어서 출력
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                    .data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        // userid가 생성한 todo entity 목록 받아옴
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        // todo entity 목록을 todo dto 목록으로 변경
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}
