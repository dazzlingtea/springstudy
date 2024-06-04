package com.study.springstudy.springmvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction {

    private long reactionId;
    private long boardNo;
    private String account;
    private ReactionType reactionType;
    private LocalDateTime reactionDate;


}
