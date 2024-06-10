package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.response.AccessTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class SnsLoginService {


    // 카카오 로그인 처리 서비스 로직
    public void kakaoLogin(Map<String, Object> requestParams) {

        // 토큰 발급 요청
        String accessToken = getKakaoAccessToken(requestParams);

        // 발급받은 토큰으로 사용자 정보 가져오기
        getKakaoUserInfo(accessToken);

    }

    // 토큰으로 사용자 정보 요청
    private void getKakaoUserInfo(String accessToken) {
        // request uri
        String requestUri = "https://kapi.kakao.com/v2/user/me";
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken );
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<Map> response = template.exchange(
                requestUri
                , HttpMethod.POST
                , new HttpEntity<>(headers)
                , Map.class
        );
        // 응답 정보 JSON 꺼내기
        Map json = response.getBody();
        log.debug("user profile: {}", json);

    }

    // 인기코드로 토큰 발급 요청
    private String getKakaoAccessToken(Map<String, Object> requestParams) {

        // 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // POST요청은 쿼리파라미터를 URI에 붙일 수 없음
        // 요청 바디에 쿼리파라미터를 넣어야 함
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParams.get("appKey"));
        params.add("redirect_uri", requestParams.get("redirect"));
        params.add("code", requestParams.get("code"));

        // 요청헤더와 요청바디를 담을 객체 생성
        HttpEntity<Object> entity = new HttpEntity<>(params, headers);

        // 카카오 인증서버로 POST 요청 보내기
        RestTemplate template = new RestTemplate();

        /**
         * - RestTemplate객체가 REST API 통신을 위한 API인데 (자바스크립트 fetch역할)
         *             - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange
         *
         *    param1: 요청 URL
         *    param2: 요청 방식 (get, post, put, patch, delete…)
         *    param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 함
         *    param4: 응답결과(JSON)를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */
//        ResponseEntity<Map> response = template.exchange(requestUri, HttpMethod.POST, entity, Map.class);
        ResponseEntity<AccessTokenDto> response = template.exchange(requestUri, HttpMethod.POST, entity, AccessTokenDto.class);

//        log.debug("response: {}", response);

//        Map<String, Object> json = response.getBody();
        AccessTokenDto json = response.getBody();
        log.debug("json: {}", json);

//        String accessToken = (String) json.get("access_token");
        String accessToken = json.getAccessToken();
        String refreshToken = json.getRefreshToken();
        log.debug("토큰: {}", accessToken);


        return accessToken;
    }

}
