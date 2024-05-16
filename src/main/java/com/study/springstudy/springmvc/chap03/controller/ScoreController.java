package com.study.springstudy.springmvc.chap03.controller;

import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET

    2. 성적 정보 등록 처리 요청
    - /score/register : POST

    3. 성적정보 삭제 요청
    - /score/remove : POST

    4. 성적정보 상세 조회 요청
    - /score/detail : GET
 */

@Controller
@RequestMapping("/score")
public class ScoreController {

    // 의존객체 설정
    private ScoreJdbcRepository repository = new ScoreJdbcRepository();

    @GetMapping("/list")
    public String list() {
        System.out.println("/score/list : GET!");
        return "score/score-list";
    }
    @PostMapping("/register")
    public String register(ScorePostDto dto) {
        System.out.println("dto = " + dto);
        System.out.println("/score/register : POST!");

        // 데이터베이스에 저장
        Score score = new Score(dto);
        repository.save(score);

        return "score/";
    }
    @PostMapping("/remove")
    public String remove() {
        System.out.println("/score/remove : POST!");
        return "score/";
    }
    @GetMapping("/detail")
    public String detail() {
        System.out.println("/score/detail : GET!");
        return "score/detail";
    }
}
