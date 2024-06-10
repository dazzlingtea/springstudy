package com.study.springstudy.springmvc.chap05.api;

import com.study.springstudy.springmvc.chap05.service.SnsLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
    public String kakaoCode(String code, HttpSession session) {
        log.info("카카오 인가코드 발급 - {}", code);

        // 토큰 발급에 필요한 파라미터 만들기
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("appKey", appKey);
        requestParams.put("redirect", redirectUri);
        requestParams.put("code", code);

        // 인증 액세스 토큰 발급 요청
        snsLoginService.kakaoLogin(requestParams,session);

        return "redirect:/";
    }

}
