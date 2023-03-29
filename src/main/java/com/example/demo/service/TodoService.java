package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j  // 로깅 레벨 관리해주는 라이브러리(Simple Logging Facade for Java)
@Service
public class TodoService {
    // TodoRepository 인터페이스 구현한 클래스를 JPA가 자동 생성, 자동 주입
    @Autowired
    private TodoRepository repository;

    public String testService() {
        // 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        // 저장: h2라는 내부 DB에 저장됨
        repository.save(entity);
        // 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        repository.save(entity);
        log.info("Entity id: {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    // 수정 후 모든 Todo List 반환
    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        // entity가 Null이 아닌 경우 실행
        final Optional<TodoEntity> optional = repository.findById(entity.getId());
        optional.ifPresent(todo -> {
            // title, done 수정 후 저장
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    // userId가 생성한 모든 Todo List 리턴
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // 엔티티 유효성 확인
    private static void validate(TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
