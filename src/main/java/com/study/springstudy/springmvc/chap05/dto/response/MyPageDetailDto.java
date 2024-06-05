package com.study.springstudy.springmvc.chap05.dto.response;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDetailDto {

    private String account;
    private String nickName;
    private String email;
    private String profile;
}
