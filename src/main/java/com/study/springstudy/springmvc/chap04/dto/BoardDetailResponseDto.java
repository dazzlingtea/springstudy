package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class BoardDetailResponseDto {

    private int boardNo;
    private String writer;
    private String title;
    private String content;
    private String regDateTime;

    @Setter
    private int likeCount; // 총 좋아요 수
    @Setter
    private int dislikeCount; // 총 싫어요 수
    @Setter
    private String userReaction; // 현재 리액션 상태

    @Setter
    private List<Reply> replies;

    public BoardDetailResponseDto(Board b) {
        this.boardNo = b.getBoardNo();
        this.writer = b.getWriter();
        this.title = b.getTitle();
        this.content = b.getContent();

        DateTimeFormatter pattern
                = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초");
        this.regDateTime = pattern.format(b.getRegDateTime());
    }
}
