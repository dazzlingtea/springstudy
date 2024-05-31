package com.study.springstudy.springmvc.interceptor;

import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper boardMapper;

    // preHandle을 구현하여
    // 로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부할 것!
    // 거부하고 로그인 페이지로 리다이렉션할 것!

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("***보드인터셉터 요청전 URI: " + request.getRequestURI() + request.getQueryString());
        String redirectUri = request.getRequestURI();
//        request.getQueryString(); // ? 다음


        if(!LoginUtil.isLoggedIn(request.getSession())) {
            response.sendRedirect("/members/sign-in?message=login-required?&redirect="+redirectUri);
            return false;
        } else {

            String[] split = redirectUri.split("/");

            String boardAccount = null;
            String loggedAccount = null;
            if (split[2].equals("delete")) {
                boardAccount = null;
                try {
                    log.debug("bno 번호: " + request.getParameter("bno"));
                    if(request.getParameter("bno") != null) {
                        int parameter = Integer.parseInt(request.getParameter("bno"));
                        boardAccount = boardMapper.findOne(parameter).getAccount();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loggedAccount = LoginUtil.getLoggedInUserAccount(request.getSession());
                log.debug("**세션계정: " + loggedAccount + "| 디비계정: " + boardAccount);

                // 삭제요청이 들어오면 서버에서 한번더 관리자인지 자기가 쓴 글인지 체크
                if(!(loggedAccount.equals(boardAccount) || loggedAccount.equals("ADMIN")) ) {
                    response.sendRedirect("/board/list");
                    return false;
                }
            }

            return true;
        }


    }
}
