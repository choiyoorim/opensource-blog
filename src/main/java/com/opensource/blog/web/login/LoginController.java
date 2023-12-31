package com.opensource.blog.web.login;

import com.opensource.blog.domain.member.Member;
import com.opensource.blog.domain.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @GetMapping(value = {"/login", "test/login"})
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }
    @PostMapping(value = {"/login", "test/login"})
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult
            bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(),
                form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //로그인 성공 처리 TODO
        return "redirect:/";
    }
}