package com.study.springstudy.webservlet.chap02.v4.controller;

import com.study.springstudy.webservlet.Model;

import java.util.Map;

public class JoinController implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Model model) {
        return "v4/reg_form";
    }
}
