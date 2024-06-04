package com.study.springstudy.springmvc.chap04.controller;

import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import com.study.springstudy.springmvc.chap05.dto.response.ReactionDto;
import com.study.springstudy.springmvc.chap05.service.ReactionService;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    //private final BoardRepository repository;
    private final BoardService boardService;
    private final ReactionService reactionService;


    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    public String list(@ModelAttribute("s") Search page, Model model) {

        List<BoardListResponseDto> bList = boardService.findList(page);

        // 페이지 정보를 생성하여 JSP에게 전송
        PageMaker maker = new PageMaker(page, boardService.getCount(page));
//        System.out.println("maker = " + maker);

        model.addAttribute("bList", bList);
        model.addAttribute("maker", maker);
//        model.addAttribute("s", page);

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
    public String write(BoardWriteRequestDto dto, HttpSession session) throws SQLException {

        boardService.register(dto, session);
        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(int bno, HttpSession session) throws SQLException {

        boardService.delete(bno, session);
        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 요청 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno,
                         Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) throws SQLException {

        model.addAttribute("bbb", boardService.detail(bno, request, response));

        // 요청 헤더를 파싱하여 이전 페이지의 주소를 얻어냄
        String ref = request.getHeader("Referer");
        model.addAttribute("ref", ref);

        return "board/detail";
    }

    // 목록 조회땐 제목 8글자, 내용 10글자까지만 조회되고..상세조회하면 조회수 상승 (DTO)
    // 좋아요 요청 비동기 처리
    @GetMapping("/like")
    @ResponseBody
    public ResponseEntity<?> like(long bno, HttpSession session) throws SQLException {

        // 로그인 검증
        if(!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");

        }

        log.info("좋아요 async request!");

        String account = LoginUtil.getLoggedInUserAccount(session);

        ReactionDto dto = reactionService.like(bno, account);// 좋아요 요청 처리
        return ResponseEntity.ok().body(dto);
    }
    // 싫어요 요청 비동기 처리
    @GetMapping("/dislike")
    @ResponseBody
    public ResponseEntity<?> dislike(long bno, HttpSession session) {

        // 로그인 검증
        if(!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");

        }

        log.info("싫어요 async request!");

        String account = LoginUtil.getLoggedInUserAccount(session);

        ReactionDto dto = reactionService.dislike(bno, account);// 싫어요 요청 처리

        return ResponseEntity.ok().body(dto);

    }
}
