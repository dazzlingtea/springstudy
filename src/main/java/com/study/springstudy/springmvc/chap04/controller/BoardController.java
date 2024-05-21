package com.study.springstudy.springmvc.chap04.controller;

import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    //private final BoardRepository repository;
    private final BoardService service;


    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String list(Model model) {

        // 3. jsp파일에 해당 목록데이터를 보냄
        model.addAttribute("bList", service.findAll());

        return "board/list";
    }

    // 2. 글쓰기 양식 화면 열기 요청 (/board/write : GET)
    @GetMapping("/write")
    public String write() {
        System.out.println("새글쓰기 버튼");
        return "board/write";
    }

    // 3. 게시글 등록 요청 (/board/write : POST)
    // -> 목록조회 요청 리다이렉션
    @PostMapping("/write")
    public String write(BoardWriteRequestDto dto) throws SQLException {
        service.register(dto);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(int bno) throws SQLException {

        service.delete(bno);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 요청 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno, Model model) throws SQLException {

        // 1. 상세조회하고 싶은 글번호를 읽기
        // 2. 데이터베이스로부터 해당 글번호 데이터 조회하기

        // 3. jsp 파일에 조회한 데이터 보내기
        model.addAttribute("bbb", service.upView(bno));
//        repository.
        return "board/detail";
    }

    // 목록 조회땐 제목 8글자, 내용 10글자까지만 조회되고..상세조회하면 조회수 상승

}
