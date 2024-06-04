package com.study.springstudy.springmvc.interceptor;

import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@Slf4j
public class ReplyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        log.debug(request.getRequestURI() +"/"+ request.getQueryString() +" : "+ request.getMethod());

        if(!LoginUtil.isLoggedIn(session)) {

            return false;
        }

        if(LoginUtil.isAdmin(session)) {
            return true;
        }

        String loggedInUserAccount = LoginUtil.getLoggedInUserAccount(session);
//        if(loggedInUserAccount)

        return true;
    }
}
