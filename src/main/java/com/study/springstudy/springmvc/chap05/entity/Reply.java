package com.study.springstudy.springmvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;


}
