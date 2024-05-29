package com.study.springstudy.springmvc.chap05;

import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReplyMapperTest {

    @Autowired BoardMapper boardMapper;
    @Autowired ReplyMapper replyMapper;


    @Test
    @DisplayName("전체조회")
    void findAllTest() {
        //given
        long boardNo = 1;
        //when
        List<Reply> replies = replyMapper.findAll(boardNo, new Page());

        //then
        replies.forEach(System.out::println);
    }
    @Test
    @DisplayName("댓글 삭제")
    void deleteTest() {
        //given
        long replyNo = 1;
        //when
        replyMapper.delete(replyNo);
        //then
    }
    @Test
    @DisplayName("2번 댓글 내용 수정수정으로 변경")
    void modifyTest() {
        //given
        long replyNo = 2;
        Reply reply = Reply.builder()
                .replyNo(replyNo)
                .replyText("수정수정")
                .build();
        //when
        replyMapper.modify(reply);
        //then
    }





//    @Test
//    @DisplayName("")
//    void bulkInsert() {
//        // 게시물 100개와 댓글 5000개를 랜덤으로 등록
//        for (int i = 1; i <= 100; i++) {
//            Board b = Board.builder()
//                    .title("글 "+ i)
//                    .writer("작성자"+i)
//                    .content("내용"+i)
//                    .build();
//            boardMapper.save(b);
//        }
//        for (int i = 1; i <= 5000; i++) {
//            Reply reply = Reply.builder()
//                    .replyText("하하호호댓글"+ i)
//                    .replyWriter("리플러"+i)
//                    .boardNo((long) (Math.random() * 100 + 1))
//                    .build();
//            replyMapper.save(reply);
//
//        }
//    }

}