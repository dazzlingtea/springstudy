package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteRequestDto {
    private String title;
    private String content;
    private String writer;
//    private int viewCount;

    public Board toEntity() {
        Board b = new Board();
        b.setContent(content);
        b.setTitle(title);
        b.setWriter(writer);
        return b;
    }

}
