package com.study.springstudy.springmvc.chap04.controller;

import com.study.springstudy.springmvc.chap04.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository repository;

    // 1. 목록 조회 요청 (/board/list : GET)


    // 2. 글쓰기 양식 화면 열기 요청 (/board/write : GET)

    // 3. 게시글 등록 요청 (/board/write : POST)
    // -> 목록조회 요청 리다이렉션

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션

    // 5. 게시글 상세 조회 요청 (/board/detail : GET)

    // 목록 조회땐 제목 8글자, 내용 10글자까지만 조회되고..상세조회하면 조회수 상승

}
