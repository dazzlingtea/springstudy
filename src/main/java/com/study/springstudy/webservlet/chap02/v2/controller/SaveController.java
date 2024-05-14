package com.study.springstudy.webservlet.chap02.v2.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.View;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chap01/save")
public class SaveController implements ControllerV2 {
    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public View process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        Member member = new Member(userName, account, password);
        repo.save(member);

//        response.sendRedirect("/chap02/show");
        return new View("redirect:/chap02/v2/show");
    }

}
