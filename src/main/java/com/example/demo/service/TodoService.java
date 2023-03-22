package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    // TodoRepository 인터페이스 구현한 클래스를 JPA가 자동 생성, 자동 주입
    @Autowired
    private TodoRepository repository;

    public String testService() {
        // 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // 저장
        repository.save(entity);
        // 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
}
