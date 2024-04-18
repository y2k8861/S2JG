package com._S2JG.controller;

import com._S2JG.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class AuthController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EmailService emailService;

    @GetMapping("/auth/email/req")
    public boolean getEmailReq(@RequestParam String memberEmail){
        System.out.println("AuthController.getEmailReq");

        // 1. 난수 이용한 6자리 인증코드 발급
        // 1-1 난수 객체 생성
        Random random = new Random( );
        // 1-2 6자리
        String ecode = ""; // 타입이 String 인 이유 : 맨앞자리에 0 붙힐려고
        for (int i = 1; i <= 6 ; i++) {
            // 1-3 난수생성 해서 변수에 누적 문자 연결하기
            ecode += random.nextInt(10); // 10미만 : 0 ~ 9 // .nextInt( 미만 ) + 시작번호

        }
        System.out.println("ecode = " + ecode); // 이메일 전송 안했을때 콘솔보고 테스트 진행을 위해 콘솔 찍어라

        // 2. HTTP 세션에 특정시간만큼 발급된 인증코드 보관
        // 1. 세션객체 에 속성 추가 해서 발급된 인증코드 대입하기
        request.getSession().setAttribute("ecode" , ecode );
        // 2. 세션에 생명주기 넣어주기
        request.getSession().setMaxInactiveInterval( 100 ); // 초 기준
        // 3. 발급된 인증코드를 인증할 이메일로 전송
        emailService.send( memberEmail , "고속 버스 예매 사이트 인증코드 부릉부릉" , "인증코드 : " + ecode );

        return true;
    }

    @GetMapping("/auth/email/check")
    public boolean getEmailCheck(@RequestParam String ecodeinput ) {
        System.out.println("AuthController.getEmailCheck");
        System.out.println("ecodeinput = " + ecodeinput);

        // 1. HTTP 세션에 보관했던 인증코드를 꺼내서
        // 1-1 세션 속성 호출
        Object object = request.getSession().getAttribute("ecode");
        // 1-2 만약에 세션 속성이 존재하면
        if (object != null) {
            String ecode = (String) object;
            // 2. 입력받은 인증코드와 생성된 인증코드와 일치여부 판단
            // 2-1 발급된 인증코드와 입력받은 인증코드와 같으면
            if (ecode.equals(ecodeinput)) {
                return true;
            }

        }   return false;
    }


}
