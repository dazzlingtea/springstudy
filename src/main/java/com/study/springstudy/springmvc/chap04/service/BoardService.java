package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Page;
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

    public List<BoardListResponseDto> findList(Page page) {
        List<Board> boards = mapper.findAll(page);

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹 (dto 내부에서)

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

    public int getCount() {
        return mapper.count();
    }
}
