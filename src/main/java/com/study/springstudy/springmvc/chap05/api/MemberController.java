package com.study.springstudy.springmvc.chap05.api;


import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.MyPageDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springstudy.springmvc.chap05.dto.response.MyPageDetailDto;
import com.study.springstudy.springmvc.chap05.service.LoginResult;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import com.study.springstudy.springmvc.util.FileUtil;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private String rootPath = "E:/spring_prj/upload";

    // 회원가입 양식 열기
    @GetMapping("/sign-up")
    public void signUp() {
        log.info("/members/sign-up GET: forwarding to sign-up.jsp");
//        return "members/sign-up";
    }

    // 회원가입 요청 처리
    @PostMapping("/sign-up")
    public String signUp(SignUpDto dto) {
        log.info("/members/sign-up POST");
        log.debug("parameters: {}", dto);

        // 프로필 이미지 추출
        MultipartFile profileImage = dto.getProfileImage();
        String profilePath = null;

        if (!profileImage.isEmpty()) {
            log.debug("attached profile img name: {}", dto.getProfileImage().getOriginalFilename());
            // 서버에 업로드 후 업로드 경로 반환
            profilePath = FileUtil.uploadFile(rootPath, profileImage);
        }

        boolean flag = memberService.join(dto, profilePath);

        return flag ? "redirect:/members/sign-in" : "redirect:/members/sign-up";
    }
    // 아이디, 이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        boolean flag = memberService.checkIdentifier(type, keyword);
        log.info("{} 중복체크 결과? {}", type,flag);
        return ResponseEntity.ok().body(flag);
    }

    // 로그인 양식 열기
    @GetMapping("/sign-in")
    public String signIn(HttpSession session
        ,@RequestParam(required = false) String redirect
        , HttpServletRequest request)
    {

        // 로그인을 한 사람이 이 요청을 보내면 돌려보낸다.
//        if(LoginUtil.isLoggedIn(session)) {
//            return "redirect:/";
//        }
        session.setAttribute("redirect", redirect);

        String uri = request.getHeader("Referer");
        log.debug("로그인폼 이전 주소: {}", uri);
        if(uri != null && !uri.contains("/sign-in")) {
            session.setAttribute("prevPage", uri);
        }

        log.info("/members/sign-in GET: forwarding to sign-in.jsp");
        return "members/sign-in";
    }
    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto,
                         RedirectAttributes ra,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        log.info("/members/sign-in POST");
        log.debug("parameter: {}", dto);


        // 세션 얻기
        HttpSession session = request.getSession();
        String prevUri = (String) session.getAttribute("prevPage");
        String prevQs = null;
        if(prevUri != null) {
            prevQs = prevUri.substring(22);
        }


        log.debug("로그인요청 이전 주소 : {}", session.getAttribute("prevPage"));
        log.debug("이전 쿼리스트링: {}", prevQs);

        LoginResult result = memberService.authenticate(dto, session, response);

        // 로그인 검증 결과를 JSP에게 보내기
        // Redirect시에 Redirect된 페이지에 데이터를 보낼 때는
        // Model객체를 사용할 수 없음
        // 왜냐면 Model객체는 request객체를 사용하는데 해당 객체는
        // 한번의 요청이 끝나면 메모리에서 제거된다. 그러나 redirect는
        // 요청이 2번 발생하므로 다른 request객체를 jsp가 사용하게 됨

//        model.addAttribute("result", result); // (X)
        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) {

            // 혹시 세션에 리다이렉트 URL이 있다면
            String redirect = (String) session.getAttribute("redirect");
            if(redirect != null) {
                session.removeAttribute("redirect");
                return "redirect:" + redirect;
            }
            return prevQs != null ? "redirect:/"+prevQs :"redirect:/index"; // 로그인 성공시
        }

        return "redirect:/members/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        // 세션 구하기
        HttpSession session = request.getSession();

        // 자동로그인 상태인지 확인
        if(LoginUtil.isAutoLogin(request)) {
            // 쿠키를 제거하고, DB에도 자동로그인 관련데이터를 원래대로 해놓음
            memberService.autoLoginClear(request, response);
        }

        // 세션에서 로그인 기록 삭제
        session.removeAttribute("login");
        // 세션을 초기화 (reset)
        session.invalidate();
        // 홈으로 보내기
        return "redirect:/";
    }

    // ======= 마이페이지

    @GetMapping("/my-page")
    public void myPage() {
        log.info("/members/my-page GET: forwarding to my-page.jsp");
    }

    @PostMapping("/my-page")
//    @ResponseBody
    public String myPage(MyPageDto dto, HttpSession session) {
        log.info("/members/my-page POST");
        log.debug("parameters: {}", dto);

        LoginUserInfoDto loginUser = LoginUtil.getLoggedInUser(session);
        if(!loginUser.getAccount().equals(dto.getAccount())) {
            return "/";
        }

        // 프로필 이미지 추출
        MultipartFile profileImage = dto.getProfileImage();
        String profilePath = null;

        if (!profileImage.isEmpty()) {
            log.debug("attached profile img name: {}", dto.getProfileImage().getOriginalFilename());
            // 서버에 업로드 후 업로드 경로 반환
            profilePath = FileUtil.uploadFile(rootPath, profileImage);
        }

        MyPageDetailDto res = memberService.updateMember(dto, profilePath, loginUser);
        if(res == null) return "/";
        return "/members/my-page";
    }

//    @PostMapping("/my-page")
//    @ResponseBody
//    public ResponseEntity<?> myPage(MyPageDto dto, HttpSession session) {
//        log.info("/members/my-page POST");
//        log.debug("parameters: {}", dto);
//
//        LoginUserInfoDto loginUser = LoginUtil.getLoggedInUser(session);
//        if(!loginUser.getAccount().equals(dto.getAccount())) {
//            return ResponseEntity.status(403)
//                    .body("로그인이 필요합니다.");
//        }
//
//        // 프로필 이미지 추출
//        MultipartFile profileImage = dto.getProfileImage();
//        String profilePath = null;
//
//        if (!profileImage.isEmpty()) {
//            log.debug("attached profile img name: {}", dto.getProfileImage().getOriginalFilename());
//            // 서버에 업로드 후 업로드 경로 반환
//            profilePath = FileUtil.uploadFile(rootPath, profileImage);
//        }
//
//        MyPageDetailDto res = memberService.updateMember(dto, profilePath);
//        if(res == null) return ResponseEntity.status(404).body(null);
//        return ResponseEntity.ok().body(res);
//    }

}
