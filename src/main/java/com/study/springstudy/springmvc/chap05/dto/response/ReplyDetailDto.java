package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.entity.Reply;
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
    private String account;
    @Setter
    private String loginAccount;

    // 엔터티를 DTO로 변환하는 생성자
    public ReplyDetailDto(Reply r) {
        this.rno = r.getReplyNo();
        this.text = r.getReplyText();
        this.writer = r.getReplyWriter();
        this.createAt = r.getReplyDate();
        this.account = r.getAccount();
    }
}
