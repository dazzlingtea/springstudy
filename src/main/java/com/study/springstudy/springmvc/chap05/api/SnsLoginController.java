package com.study.springstudy.springmvc.chap05.api;

import com.study.springstudy.springmvc.chap05.service.SnsLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SnsLoginController {

    private final SnsLoginService snsLoginService;

    @Value("${sns.kakao.app-key}")
    private String appKey;
    @Value("${sns.kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/kakao/login")
    public String kakaoLogin() {

        // 카카오로 인가코드발급 통신을 해야함
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id="+appKey;
        uri += "&redirect_uri="+redirectUri;
        uri += "&response_type=code";

        return "redirect:" + uri;
    }

    // 인가코드를 받는 요청 메서드
    @GetMapping("/oauth/kakao")
    public String kakaoCode(String code) {
        log.info("카카오 인가코드 발급 - {}", code);

        // 인증 액세스 토큰 발급 요청


        return "";
    }

}
