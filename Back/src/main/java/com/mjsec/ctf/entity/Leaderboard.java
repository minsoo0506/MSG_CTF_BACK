package com.mjsec.ctf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Getter
@Setter
@Table(name = "leaderboard")  // 테이블 이름 명시
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id 자동 증가
    @Column(name = "id")  // DB 컬럼명 매칭
    private Long id;

    @Column(name = "user_id")  // user_id 컬럼 매핑
    private String userid;

    @Column(name = "total_point")  // total_point 컬럼 매핑
    private int totalPoint;

    @Column(name = "last_solved_time")  // last_solved_time 컬럼 매핑
    private LocalDateTime lastSolvedTime;

    @Column(name = "univ")
    private String univ;

}