package com._S2JG.controller;

import com._S2JG.model.dto.InquiryDto;
import com._S2JG.model.dto.PageDto;
import com._S2JG.service.InquiryService;
import com._S2JG.service.MemberService;
import com._S2JG.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    // 1. 문의 글 작성

    @PostMapping("/inquiryWrite.do")
    @ResponseBody
    public boolean doPostInquiryWrite(InquiryDto inquiryDto){
        System.out.println("InquiryController.doPostInquiryWrite");
        System.out.println("inquiryDto = " + inquiryDto);

        // 1. 현재 로그인된 세션(브라우저 마다 톰캣서버(자바프로그램) 메모리(JVM) 저장소) 호출
        Object object = request.getSession().getAttribute("loginDto");
        if ( object == null ) return false; // 세션없다(로그인안했다)

        // 2. 형변환
        String memberId = (String)object;

        // 3. mid를 mno 찾아오기
        long memberNo = memberService.doGetLoginInfo( memberId ).getMemberNo();

        // 4. 작성자번호 대입
        inquiryDto.setMemberNo( memberNo );

        return inquiryService.doPostInquiryWrite( inquiryDto );
    }

    // 2. 문의 함
    @GetMapping("/inquiry.do")
    @ResponseBody
    public PageDto doGetInquiryView(@RequestParam int page, @RequestParam int pageBoardSize, @RequestParam String key, @RequestParam String value ){
        System.out.println("InquiryController.doGetInquiryView");
        System.out.println("page = " + page + ", pageBoardSize = " + pageBoardSize + ", key = " + key + ", value = " + value);

        return inquiryService.doGetInquiryView( page , pageBoardSize , key , value );

    }

    // 3. 문의 함 개별 조회
    @GetMapping("/inquiryView.do")
    @ResponseBody
    public InquiryDto doGetInquiryInoView(@RequestParam int ino ){
        System.out.println("InquiryController.doGetInquiryInoView");
        System.out.println("ino = " + ino);

        return inquiryService.doGetInquiryInoView( ino );
    }

    // 4. 문의 함 수정
    @PutMapping("/inquiryUpdate.do")
    @ResponseBody
    public boolean doPutInquiryUpdate( InquiryDto inquiryDto ){
        System.out.println("문의함수정InquiryController.doPutInquiryUpdate");
        System.out.println("inquiryDto = " + inquiryDto);

//        Object object = request.getSession().getAttribute("loginDto");
//        if ( object != null ){
//            String mid = (String)object;
//
//            boolean result = userService.ReservationWriterAuth( inquiryDto.getIno() , mid );
//            if ( result ){
//                return inquiryService.doPutInquiryUpdate( inquiryDto );
//            }
//        }

        return inquiryService.doPutInquiryUpdate( inquiryDto );
    }

    // 5. 문의 함 삭제
    @DeleteMapping("/inquiryDelete.do")
    @ResponseBody
    public boolean doDeleteInquiry(@RequestParam int ino ){
        System.out.println("InquiryController.doDeleteInquiry");
        System.out.println("ino = " + ino);
        return inquiryService.doDeleteInquiry( ino );
    }

    // 페이지 반환 ========================================================
    @GetMapping("/inquiryWrite")
    public String getInquiryWrite(){
        return "inquiryWrite";
    }

    @GetMapping("/inquiry")
    public String getInquiry(){
        return "inquiry";
    }

    @GetMapping("/inquiryView")
    public String getinquiryView(){ return "inquiryView"; }

    @GetMapping("/inquiryUpdate")
    public String getinquiryUpdate(){ return "inquiryUpdate"; }

}
