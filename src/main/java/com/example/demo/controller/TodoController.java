package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> createTodo(
            @AuthenticationPrincipal String userId, // 필터에서 사용자 인증 시 id 값이 여기로 전달됨
            @RequestBody TodoDTO dto
    ) {
        try {
            // dto로 entity 생성
            TodoEntity entity = TodoDTO.toEntity(dto);
            // id null처리: 생성 당시에는 id 없어야 함
            entity.setId(null);
            // userId 설정
            entity.setUserId(userId);
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
    public ResponseEntity<?> updateTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {
        // dto를 entity로 변경
        TodoEntity entity = TodoDTO.toEntity(dto);
        // userid 저장
        entity.setUserId(userId);
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
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto
    ) {
        try {
            // dto를 entity로 변경
            TodoEntity entity = TodoDTO.toEntity(dto);
            // userid 저장
            entity.setUserId(userId);
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
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        // userid가 생성한 todo entity 목록 받아옴
        List<TodoEntity> entities = service.retrieve(userId);
        // todo entity 목록을 todo dto 목록으로 변경
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        // response 만들어서 출력
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder()
                .data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}
