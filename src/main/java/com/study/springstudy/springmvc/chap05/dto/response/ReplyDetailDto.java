package com.study.springstudy.springmvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyFindAllDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @Builder
public class ReplyDetailDto {
//    @JsonProperty(reply_no)
    private long rno;
    private String text;
    private String writer;
//    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime createAt;
    private String account;  // 댓글 작성자 계정명
    @JsonProperty("profile")
    private String profileImg;

    // 엔터티를 DTO로 변환하는 생성자
    public ReplyDetailDto(ReplyFindAllDto r) {
        this.rno = r.getReplyNo();
        this.text = r.getReplyText();
        this.writer = r.getReplyWriter();
        this.createAt = r.getReplyDate();
        this.account = r.getAccount();
        this.profileImg = r.getProfileImg();
    }
}
