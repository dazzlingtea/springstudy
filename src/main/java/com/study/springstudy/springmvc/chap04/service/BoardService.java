package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reaction;
import com.study.springstudy.springmvc.chap05.entity.ViewLog;
import com.study.springstudy.springmvc.chap05.mapper.ReactionMapper;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springstudy.springmvc.chap05.mapper.ViewLogMapper;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.springstudy.springmvc.util.LoginUtil.getLoggedInUserAccount;
import static com.study.springstudy.springmvc.util.LoginUtil.isLoggedIn;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;
    private final ViewLogMapper viewLogMapper;
//    private final LikeLogMapper likeLogMapper;
    private final ReactionMapper reactionMapper;

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
    public BoardDetailResponseDto detail(int bno,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws SQLException
    {
        Board b = findOne(bno);

        HttpSession session = request.getSession();

        // 비회원이거나 본인 글이면 조회수 증가 방지
        // 로그인 계정명
        String currentUserAccount = getLoggedInUserAccount(session);

        // 상세조회시 초기렌더링에 그려질 데이터
        BoardDetailResponseDto responseDto = new BoardDetailResponseDto(b);
        responseDto.setLikeCount(reactionMapper.countLikes(bno));
        responseDto.setDislikeCount(reactionMapper.countDislikes(bno));
        Reaction reaction = reactionMapper.findOne(bno, currentUserAccount);
        String type = null;
        if(reaction != null) {
            type = reaction.getReactionType().toString();
        }
        responseDto.setUserReaction(type);

        if(!isLoggedIn(session) || LoginUtil.isMine(b.getAccount(), currentUserAccount)) {
            return responseDto;
        }
        // 조회수가 올라가는 조건 처리 (쿠키버전)
//        if(shouldIncreaseViewCount(bno, request, response)) {
//                boardMapper.upViewCount(bno);
//
//        }

        // 조회수가 올라가는 조건처리 (데이터베이스 버전)
        // 1. 지금 조회하는 글이 기록에 있는지 확인
        int boardNo = b.getBoardNo(); // 게시물 번호
        ViewLog viewLog = viewLogMapper.findOne(currentUserAccount, boardNo);

        boolean shouldIncrease = false;
        ViewLog viewLogEntity = ViewLog.builder()
                .account(currentUserAccount)
                .boardNo(boardNo)
                .viewTime(LocalDateTime.now())
                .build();

        if(viewLog == null) {
            // 2. 이 게시물이 이 회원에 의해 처음 조회됨
            viewLogMapper.insertViewLog(
                    viewLogEntity
            );
            shouldIncrease = true;
        } else {
            // 3. 조회 기록이 있는 경우 - 1시간 이내 인지
            // 혹시 1시간이 지난 게시물인지 확인
            LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
            if(viewLog.getViewTime().isBefore(oneHourAgo)) {
                // 4. db에서 view_time 수정
                viewLogMapper.updateViewLog(viewLogEntity);
                shouldIncrease = true;
            }
        }
        if(shouldIncrease) {
            boardMapper.upViewCount(boardNo);
        }

//        // 좋아요 조건 처리
//        LikeLog like = likeLogMapper.findLikeAccount(currentUserAccount, boardNo);
//        if(like == null) {
//            likeLogMapper.insertLike(
//                    LikeLog.builder()
//                            .boardNo(b.getBoardNo())
//                            .account(currentUserAccount)
//                            .build()
//            );
//
//        } else {
//            likeLogMapper.deleteLike(b.getAccount(), boardNo);
//        }



        return responseDto;


//        HttpSession session = request.getSession();
//        if(isLoggedIn(session)) {
//            String loginAccount = LoginUtil.getLoggedInUser(session).getAccount();
//            Cookie foundCookie = WebUtils.getCookie(request, "" + bno);
//
//            if(b != null && !isMine(b.getAccount(), loginAccount) && foundCookie == null) {
//                boardMapper.upViewCount(bno);
//                Cookie boardCookie = new Cookie(""+bno, session.getId());
//                boardCookie.setPath("/board/");
//                boardCookie.setMaxAge(60*60);
//                response.addCookie(boardCookie);
//            }
//
//        }

        // 댓글 목록 조회
//        List<Reply> replies = replyMapper.findAll(bno);

//        BoardDetailResponseDto dto = new BoardDetailResponseDto(b);
////        dto.setReplies(replies);
//
//        return dto;
    }
    // 조회수 증가 여부를 판단
    /*
        - 만약 내가 처음 조회한 상대방의 게시물이면 해당 게시물 번호로 쿠키를 생성
        - 쿠키 안에는 생성 시간을 저장, 수명은 1시간으로 설정
        - 이후 게시물 조회시 반복해서 쿠키를 조회한 후 해당 쿠키가 존재할 시 false를 리턴하여 조회수증가를 방지
        - 쿠키 생성 예시
          Cookie(name=view_101, 2024-06-03 14:11:30)
     */
    private boolean shouldIncreaseViewCount(int bno, HttpServletRequest request, HttpServletResponse response) {
        // 쿠키 검사
        String cookieName = "view_"+bno;
        Cookie viewCookie = WebUtils.getCookie(request, cookieName); // 쿠키 없으면 null 리

        // 이 게시물에 대한 쿠키가 존재 -> 아까 조회한 게시물
        if(viewCookie != null) {
            return false;
        }

        // 쿠키 생성
        makeViewCookie(cookieName, response);

        return true;
    }

    // 조회수 쿠키를 생성하고 클라이언트에 전송하는 메서드
    private void makeViewCookie(String cookieName, HttpServletResponse response) {
        Cookie newCookie = new Cookie(cookieName, LocalDateTime.now().toString());
        newCookie.setPath("/"); // 쿠키 사용 범위 결정
        newCookie.setMaxAge(60 * 60);
        response.addCookie(newCookie);
    }

    public int getCount(Search search) {
        return boardMapper.count(search);
    }
}
