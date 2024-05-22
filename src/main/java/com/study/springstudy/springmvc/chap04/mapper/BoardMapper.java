package com.study.springstudy.springmvc.chap04.mapper;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 게시물 목록 조회
    List<Board> findAll(Search page);

    // 게시물 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    boolean save(Board board);

    // 게시물 삭제
    boolean delete(int boardNo);

    // 조회수 갱신
    boolean upViewCount(int boardNo);

    // 총 게시물 수 조회
    int count(Search page);
}
