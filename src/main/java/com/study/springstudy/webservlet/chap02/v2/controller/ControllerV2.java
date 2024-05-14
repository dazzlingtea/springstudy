package com.study.springstudy.webservlet.chap02.v2.controller;

import com.study.springstudy.webservlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    // 각 요청에 맞는 적절한 처리를 하는 기능
    View process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
