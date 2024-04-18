package com._S2JG.controller;

import com._S2JG.model.dto.LoginDto;
import com._S2JG.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;


    @GetMapping("/login/check")
    @ResponseBody
    public String doGetLoginCheck(){
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        if(sessionObj != null){
            loginDto = (String) sessionObj;
        }
        return loginDto;
    }

    @GetMapping("/login/info")
    @ResponseBody
    public LoginDto doGetLoginInfo(LoginDto loginDto ){
        System.out.println("LoginController.doGetLoginInfo");
        return memberService.doGetLoginInfo( loginDto.getMemberid() ); // 서비스 요청과 응답 전달
    }
}
