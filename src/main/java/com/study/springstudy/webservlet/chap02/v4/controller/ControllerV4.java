package com.study.springstudy.webservlet.chap02.v4.controller;

import com.study.springstudy.webservlet.MyModel;

import java.util.Map;

public interface ControllerV4 {

    /**
     * 요청이 들어오면 적절한 처리를 수행
     * @param paramMap : 요청 정보 ( 쿼리파라미터 ) 를 모두 읽어서 맵에 담음
     * @param myModel : 응답시 보여줄 JSP에 보낼 데이터를 담는 수송객체
     * @return - 응답시 포워딩하거나 리다이렉트할 경로문자열
     */
    String process(Map<String, String> paramMap, MyModel myModel);
}
