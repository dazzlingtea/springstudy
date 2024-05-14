package com.study.springstudy.webservlet.chap02.v1.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveController implements ControllerV1{
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.회원가입 폼에서 넘어온 데이터 읽기
        String userName = request.getParameter("userName");
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        // 2.회원 정보를 객체로 포장하여 적절한 저장소에 저장
        Member member = new Member(account, password, userName);
//        System.out.println("member = " + member);
        repo.save(member);

        //3. 적절한 페이지로 이동 - 조회화면으로 리다이렉트
        response.sendRedirect("/chap01/show");
    }
}
