package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 이거 생성하는 것만으로 DB 연결 끝!!!
@Repository     // 예도 @Component 어노테이션 포함하므로 Bean임
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // JpaRepository<테이블에 매핑될 엔터티 클래스, 기본 키 타입>

}
