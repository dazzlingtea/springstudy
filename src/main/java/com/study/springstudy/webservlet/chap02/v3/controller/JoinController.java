package com.study.springstudy.webservlet.chap02.v3.controller;


import com.study.springstudy.webservlet.ModelAndView;

import java.util.Map;

public class JoinController implements ControllerV3 {

    @Override
    public ModelAndView process(Map<String, String> paramMap) {
        return new ModelAndView("v3/reg_form");
    }
}
