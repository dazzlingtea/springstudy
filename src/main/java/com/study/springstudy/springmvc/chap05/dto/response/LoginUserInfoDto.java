package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfoDto {

    // 클라이언트에 보낼 정보
    private String account;
    private String nickName;
    private String email;
    private String auth;

    public LoginUserInfoDto(Member member) {
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.nickName = member.getName(); // ** 처리 여기서
        this.auth = member.getAuth().name();
        System.out.println(this);
    }


}
