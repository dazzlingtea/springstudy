package com.study.springstudy.springmvc.chap04.common;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private int pageNo; // 클라이언트가 요청한 페이지번호
    private int amount; // 클라이언트가 요청한 한 페이지당 게시물 목록 수

    /*
        만약에 한 페이지에 게시물을 10개씩 렌더링한다면
        1페이지 -> LIMIT 0, 10
        2페이지 -> LIMIT 10, 10
        3페이지 -> LIMIT 20, 10

        만약에 한 페이지에 게시물을 6개씩 렌더링한다면
        1페이지 -> LIMIT 0, 6
        2페이지 -> LIMIT 6, 6
        3페이지 -> LIMIT 12, 6

        만약에 한 페이지에 게시물을 N개씩 렌더링한다면
        1페이지 -> LIMIT 0, N
        2페이지 -> LIMIT 6, N
        3페이지 -> LIMIT 12, N
        M페이지 -> LIMIT (M - 1) * N, N
     */
    public int getPageStart() {
        return (this.pageNo - 1) * this.amount;
    }

}
