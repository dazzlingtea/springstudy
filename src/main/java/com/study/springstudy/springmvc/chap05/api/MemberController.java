package com.study.springstudy.springmvc.chap05.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@Slf4j
public class MemberController {

    @GetMapping("/sign-up")
    public String signUp() {
        log.info("sign-up GET: forwarding to sign-up.jsp");
        return "members/sign-up";
    }

}
