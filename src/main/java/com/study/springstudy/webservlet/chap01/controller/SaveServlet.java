package com.study.springstudy.webservlet.chap01.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chap01/save")
public class SaveServlet extends HttpServlet {
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.회원가입 폼에서 넘어온 데이터 읽기
        String userName = req.getParameter("userName");
        String account = req.getParameter("account");
        String password = req.getParameter("password");

        // 2.회원 정보를 객체로 포장하여 적절한 저장소에 저장
        Member member = new Member(userName, account, password);
//        System.out.println("member = " + member);
        repo.save(member);

        //3. 적절한 페이지로 이동 - 조회화면으로 리다이렉트
        resp.sendRedirect("/chap01/show");
    }
}
