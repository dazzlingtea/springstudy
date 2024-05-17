package com.study.springstudy.webservlet.chap02.v4.controller;

import com.study.springstudy.webservlet.MyModel;

import java.util.Map;

public class JoinController implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, MyModel myModel) {
        return "v4/reg_form";
    }
}
