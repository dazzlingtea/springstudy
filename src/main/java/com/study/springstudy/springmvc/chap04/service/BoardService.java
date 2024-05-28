package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
    public boolean register(BoardWriteRequestDto dto) throws SQLException {
        Board b = dto.toEntity();
        return boardMapper.save(b);
    }

    public boolean delete(int bno) throws SQLException {
        // 삭제권한 확인 시 여기에 작성
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
    public BoardDetailResponseDto detail(int bno) throws SQLException {
        Board b = findOne(bno);
        if(b != null) boardMapper.upViewCount(bno);

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
