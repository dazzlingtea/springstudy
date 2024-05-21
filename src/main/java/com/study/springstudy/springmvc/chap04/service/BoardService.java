package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper mapper;

    public List<BoardListResponseDto> findList() {
        List<Board> boards = mapper.findAll();

        return boards.stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }
    public boolean register(BoardWriteRequestDto dto) throws SQLException {
        Board b = dto.toEntity();
        return mapper.save(b);
    }

    public boolean delete(int bno) throws SQLException {
        // 삭제권한 확인 시 여기에 작성
        return mapper.delete(bno);
    }
    public Board findOne(int bno) throws SQLException {
        return mapper.findOne(bno);
    }
    public BoardDetailResponseDto upView(int bno) throws SQLException {
        if(mapper.upViewCount(bno)) {
            Board b = findOne(bno);
            return new BoardDetailResponseDto(b);
        } else {
            return null;
        }
    }
    public BoardDetailResponseDto detail(int bno) throws SQLException {
        Board b = findOne(bno);
        if(b != null) mapper.upViewCount(bno);
        return new BoardDetailResponseDto(b);
    }

}
