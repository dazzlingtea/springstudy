package com.study.springstudy.springmvc.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class ErrorController {

    @GetMapping("/error/404")
    public String error404() {
        return "error/error404";
    }

    @GetMapping("/error/500")
    public String error500() {
        return "error/error500";
    }

    @GetMapping("/access-deny")
    public String accessDeny(String message, Model model, HttpServletRequest request) {
        String ref = request.getHeader("Referer");
        log.debug("이전 : " + ref);
        model.addAttribute("ref", ref);
        model.addAttribute("msg", message);
        return "error/access-deny";
    }
}
