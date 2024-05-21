package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 서버에서 조회한 데이터 중 화면에 필요한 데이터만 모아놓은 클래스
@Getter
public class BoardListResponseDto {

    /* // 클라개발자와 소통해서 필드명 정해야 함
        {
            shortTitle:"",
            ....
        }
     */

    private int bno; // 원본 게시물 번호
    private String shortTitle; // 5글자 이상 줄임 처리된 제목
    private String shortContent; // 30자 이상 줄임 처리된 글 내용
    private String date; // 포맷팅된 날짜문자열
    private int view; // 조회 수


    // 엔터티를 DTO로 변환하는 생성자
    public BoardListResponseDto(Board b) {
        this.bno = b.getBoardNo();
        this.shortTitle = makeShortTitle(b.getTitle());
        this.shortContent = makeShortContent(b.getContent());
        this.date = dateFormatting(b.getRegDateTime());
        this.view = b.getViewCount();
    }

    private String dateFormatting(LocalDateTime regDateTime) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return pattern.format(regDateTime);
    }

    private String makeShortContent(String content) {
        return (content.length() > 30)
                ? content.substring(0, 30) + "..."
                : content;
    }

    private String makeShortTitle(String title) {
        return (title.length() > 5)
                ? title.substring(0, 5) + "..."
                : title;
   }

}