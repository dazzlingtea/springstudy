package com.study.springstudy.webservlet.chap02.v3.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.entity.Member;

import java.util.Map;

public class SaveController implements ControllerV3{
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public ModelAndView process(Map<String, String> paramMap) {

        String userName =paramMap.get("userName");
        String account = paramMap.get("account");
        String password = paramMap.get("password");

        Member member = new Member(account, password, userName);
        repo.save(member);

        return new ModelAndView("redirect:/chap02/v3/show");

    }
}
