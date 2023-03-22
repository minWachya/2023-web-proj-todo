package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 이거 생성하는 것만으로 DB 연결 끝!!!
// JpaRepository가 findById, save 등 기본 메서드 제공해줌
@Repository     // 예도 @Component 어노테이션 포함하므로 Bean임
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // JpaRepository<테이블에 매핑될 엔터티 클래스, 기본 키 타입>

    // JpaRepository가 제공해주지 않는 건 네이밍 규칟만 맞춰서 추가하면 됨
    // Jpa가 자동으로 쿼리 만들어줌...ㄷㄷ
    List<TodoEntity> findByUserId(String userId);
    /* 위의 내용 수동 생성 방법
    * @Query("select * from t where t.userId =?1")
    * List<TodoEntity> findByUserId(String userId);
    * */
}
