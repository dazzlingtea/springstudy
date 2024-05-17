package com.study.springstudy.springmvc.chap04.repository;

import com.study.springstudy.springmvc.chap04.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardSpringJdbcRepository implements BoardRepository {

    private final JdbcTemplate template;

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public Board findOne(int boardNo) {
        return null;
    }

    @Override
    public boolean save(Board board) {
        return false;
    }

    @Override
    public boolean delete(int boardNo) {
        return false;
    }
}
