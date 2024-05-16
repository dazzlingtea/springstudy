package com.study.springstudy.springmvc.chap03.controller;

import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String list(Model model) {
        System.out.println("/score/list : GET!");

        List<Score> scoreList = repository.findAll();
        model.addAttribute("sList", scoreList);
        return "score/score-list";
    }
    @PostMapping("/register")
    public String register(ScorePostDto dto) {
        System.out.println("dto = " + dto);
        System.out.println("/score/register : POST!");

        // 데이터베이스에 저장
        Score score = new Score(dto);
        repository.save(score);

        // 다시 조회로 돌아가야 저장된 데이터를 볼 수 있음
        // 포워딩이 아닌 리다이렉트로 재요청을 넣어야 새롭게 디비를 조회
        return "redirect:/score/list"; // 요청URL
    }
    @PostMapping("/remove")
    public String remove() {
        System.out.println("/score/remove : POST!");
        return "score/";
    }
    @GetMapping("/detail")
    public String detail(Long stuNum, Model model) {
        System.out.println("/score/detail : GET!");

        // 1. 상세조회를 원하는 학번을 읽기
        // 2. DB에 상세조회 요청
        Score score = repository.findOne(stuNum);
        // 3. DB에서 조회한 회원정보 JSP에게 전달
        model.addAttribute("s", score);
        // 4. rank 조회
        int[] result = repository.findRankByStuNum(stuNum);
        model.addAttribute("rank", result[0]);
        model.addAttribute("count", result[1]);


        return "score/score-detail";
    }
}