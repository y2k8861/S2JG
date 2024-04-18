package com._S2JG.controller;

import com._S2JG.model.dao.MemberDao;
import com._S2JG.model.dto.MemberDto;
import com._S2JG.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberService memberService;

    // == 김건우 ============================================================== //

    // == 회원가입 페이지 요청 =================== //
    @GetMapping("/member")
    public String viewSignup(){
        System.out.println("MemberController.viewSignup");
        return "/signup";
    }
    // == 회원가입 페이지 요청 END =================== //

    // == 회원가입 처리 요청 =================== //
    @PostMapping("/member/signup")
    @ResponseBody // 응답 방식 application/json;
    public boolean doPostSignup( MemberDto memberDto ){
        System.out.println("MemberController.doPostSignup");
        System.out.println("memberDto = " + memberDto);

        return memberService.doPostSignup( memberDto );
    }
    // == 회원가입 처리 요청 END =================== //

    // == 회원가입 아이디 중복 체크 요청 =================== //
    @GetMapping("/member/find/idcheck")
    @ResponseBody
    public boolean doGetFindIdCheck(@RequestParam String memberId){
        System.out.println("MemberController.doGetFindIdCheck");
        System.out.println("id = " + memberId);
        return memberService.doGetFindIdCheck( memberId );
    }
    // == 회원가입 아이디 중복 체크 요청 END =================== //

    // == 회원가입 전화번호 중복 체크 요청 =================== //
    @GetMapping("/member/find/phonecheck")
    @ResponseBody
    public boolean doGetFindPhoneCheck(@RequestParam String memberPhone){
        System.out.println("MemberController.doGetFindPhoneCheck");
        System.out.println("memberPhone = " + memberPhone);
        return memberService.doGetFindPhoneCheck( memberPhone );
    }
    // == 회원가입 전화번호 중복 체크 요청 END =================== //

    // == 회원가입 이메일 중복 체크 요청 =================== //
    @GetMapping("/member/find/emailcheck")
    @ResponseBody
    public boolean doGetFindEmailCheck(@RequestParam String memberEmail){
        System.out.println("MemberController.doGetFindEmailCheck");
        System.out.println("memberEmail = " + memberEmail);
        return memberService.doGetFindEmailCheck( memberEmail );
    }
    // == 회원가입 이메일 중복 체크 요청 END =================== //
    // == 김건우 ============================================================== //








}
