package com._S2JG.controller;

import com._S2JG.model.dao.LoginDao;
import com._S2JG.model.dto.LoginDto;
import com._S2JG.service.MemberService;
import jakarta.mail.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LoginDao loginDao;

    @Autowired
    private MemberService memberService;

    //로그인 페이지 요청
    @GetMapping("/member/login")
    public String doGetLogin(){


        return "/login";
    }

    //로그인 처리 요청
    @PostMapping("/member/login")
    @ResponseBody
    public boolean doPostLogin(LoginDto loginDto, HttpServletRequest request ){


        boolean result = loginDao.doPostLogin( loginDto ); // Dao처리

        if( result ){ // 만약에 로그인 성공이면 세션 부여
            // 세션에 저장할 내용물들을 구성( 식별키 만 )

            request.getSession().setAttribute( "loginDto" , loginDto.getMemberid() );    // loginDto : 3
            request.getSession().setAttribute( "memberGrant" , loginDto.getMemberGrant() );
            System.out.println("result = " + result);

        }
        return result; // Dao 요청후 응답 결과를 보내기
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public boolean doGetLogout(){

        request.getSession().invalidate();
        System.out.println("LoginController.doGetLogout");
        return true;
    }


    @GetMapping("/member/login/check")
    @ResponseBody
    public String doGetLoginCheck(){
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
                if(sessionObj != null){
                    loginDto = (String) sessionObj;
                    System.out.println("LoginController.doGetLoginCheck");
                }
        System.out.println("로그인Dto : " + loginDto);
        return loginDto;
    }


    // 3 ============== 회원정보 요청 ( 로그인된 회원 요청 , 패스워드 제외 ) ============
    @GetMapping("/member/login/info")
    @ResponseBody
    public LoginDto doGetLoginInfo( LoginDto loginDto ){
        System.out.println("LoginController.doGetLoginInfo");
        return memberService.doGetLoginInfo( loginDto.getMemberid() ); // 서비스 요청과 응답 전달
    }


} // controller end
/*
*
*  다른 클래스 불러올때 메소드 불러오는 방법 5가지
*
*   1.
*       클래스명 변수명 = new 생성자();
*       변수명.함수명();
*
*   1-2
*       new 생성자().함수명();
*
*   2. 만약에 해당 함수가 static
*       클래스명.함수명();
*
*   3. 만약에 싱글톤 객체가 있으면
*       클래스명.getInstance().함수명()
*
*   4.
*   @Autowrited
*
*
*
* */