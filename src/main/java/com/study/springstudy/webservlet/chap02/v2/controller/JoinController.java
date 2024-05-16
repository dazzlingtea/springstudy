package com.study.springstudy.webservlet.chap02.v2.controller;

import com.study.springstudy.webservlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JoinController implements ControllerV2{

    @Override
    public View process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return new View("v2/reg_form");
    }

}