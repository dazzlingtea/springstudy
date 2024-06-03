package com.study.springstudy.springmvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewLog {

    private long id;
    private String account;
    private long boardNo;
    private LocalDateTime viewTime;

}
