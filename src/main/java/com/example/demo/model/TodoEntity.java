package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor  // 롬복: 인자없는 생성자 생성
@AllArgsConstructor
@Data               // 롬복: getter/setter 추가
@Entity             // 엔티티 클래스 지정
@Table(name = "Todo")   //  테이블 이름 지정(미지정 시 기본 이름은 TodoEntity, To.do 테이블과 매핑)
public class TodoEntity {
    @Id             // 기본 키 지정
    @GeneratedValue(generator = "system-uuid")   // system-uuid라는 이름의 generator 이용하여 uuid 자동 생성
    @GenericGenerator(name="system-uuid", strategy = "uuid")    // system-uuid라는 이름의 generator를 생성함. 얘는 uuid라는 문자열 이용해 id 생성함.
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
