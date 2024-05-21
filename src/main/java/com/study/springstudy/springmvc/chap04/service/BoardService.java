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

    public List<BoardListResponseDto> findAll() {
        List<Board> boards = mapper.findAll();

        return boards.stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }
    public boolean register(BoardWriteRequestDto dto) throws SQLException {

        Board board = new Board(dto);
        return mapper.save(board);
    }

    public boolean delete(int bno) throws SQLException {
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

}
