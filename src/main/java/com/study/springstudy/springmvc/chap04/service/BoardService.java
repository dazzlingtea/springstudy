package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.springstudy.springmvc.util.LoginUtil.isLoggedIn;
import static com.study.springstudy.springmvc.util.LoginUtil.isMine;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    public List<BoardListResponseDto> findList(Search page) {
        List<BoardFindAllDto> boards = boardMapper.findAll(page);

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹 (dto 내부에서)

        return boards.stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }
    // 등록 요청 중간처리
    public boolean register(BoardWriteRequestDto dto, HttpSession session) throws SQLException {
        Board b = dto.toEntity();
        // 계정명을 엔터티에 추가 - 세션에서 계정명 가져오기
        b.setAccount(LoginUtil.getLoggedInUserAccount(session));
        return boardMapper.save(b);
    }

    public boolean delete(int bno, HttpSession session) throws SQLException {
        // 삭제권한 확인 시 여기에 작성
//        String loginAccount = LoginUtil.getLoggedInUserAccount(session);
//        Board b = boardMapper.findOne(bno);
//
//        if(!loginAccount.equals("ADMIN") && !b.getAccount().equals(loginAccount)) {
//            log.debug("게시글 계정: "+b.getAccount()+" 로그인계정: "+loginAccount);
//            return false;
//        }

        return boardMapper.delete(bno);
    }
    public Board findOne(int bno) throws SQLException {
        return boardMapper.findOne(bno);
    }
    public BoardDetailResponseDto upView(int bno) throws SQLException {
        if(boardMapper.upViewCount(bno)) {
            Board b = findOne(bno);
            return new BoardDetailResponseDto(b);
        } else {
            return null;
        }
    }
    public BoardDetailResponseDto detail(int bno, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Board b = findOne(bno);
        HttpSession session = request.getSession();
        if(isLoggedIn(session)) {
            String loginAccount = LoginUtil.getLoggedInUser(session).getAccount();
            Cookie foundCookie = WebUtils.getCookie(request, "" + bno);

            if(b != null && !isMine(b.getAccount(), loginAccount) && foundCookie == null) {
                boardMapper.upViewCount(bno);
                Cookie boardCookie = new Cookie(""+bno, session.getId());
                boardCookie.setPath("/board/");
                boardCookie.setMaxAge(60*60);
                response.addCookie(boardCookie);
            }

        }

        // 댓글 목록 조회
//        List<Reply> replies = replyMapper.findAll(bno);

        BoardDetailResponseDto dto = new BoardDetailResponseDto(b);
//        dto.setReplies(replies);

        return dto;
    }

    public int getCount(Search search) {
        return boardMapper.count(search);
    }
}
