package com.study.springstudy.springmvc.chap04.repository;

import com.study.springstudy.springmvc.chap04.entity.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BoardRepositoryImplTest {

    @Autowired
    private BoardRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 5; i++) {
            Board b = new Board();
            b.setTitle("Title" + i);
            b.setWriter("Writer" + i);
            b.setContent("Content" + i);
            repository.save(b);
            System.out.println("b = " + b);
        }
    }

    @Test
    @DisplayName("전체 게시글 목록 조회")
    void findAllTest() {
        //given
        //when
        List<Board> boards = repository.findAll();
        //then
        assertEquals(5, boards.size());
        assertEquals("Title0", boards.get(0).getTitle());
        boards.forEach(System.out::println);
    }

    @Test
    @DisplayName("게시글 하나 조회")
    void findOneTest() {
        //given
        int id = repository.findAll().get(0).getBoardNo();
        //when
        Board one = repository.findOne(id);
        //then
        assertNotNull(one);
        assertEquals("Title0", one.getTitle());
    }

    @Test
    @DisplayName("저장 확인")
    void saveTest() {
        //given
        Board b = new Board();
        b.setTitle("테스트");
        b.setContent("내용");
        b.setWriter("글쓴이");
        //when
        boolean flag = repository.save(b);
        //then
        assertTrue(flag);
        assertEquals(6, repository.findAll().size());
    }

    @Test
    @DisplayName("삭제 확인")
    void deleteTest() {
        //given
        int id = repository.findAll().get(0).getBoardNo();
        //when
        boolean flag = repository.delete(id);
        //then
        assertTrue(flag);
        assertEquals(4, repository.findAll().size());
    }



}