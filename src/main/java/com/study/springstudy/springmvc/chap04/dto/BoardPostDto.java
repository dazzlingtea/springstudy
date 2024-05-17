package com.study.springstudy.springmvc.chap04.dto;

import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardPostDto {
    private String title;
    private String content;
    private String writer;
//    private int viewCount;
}
