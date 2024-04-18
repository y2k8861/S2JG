package com._S2JG.service;

import com._S2JG.model.dao.MemberDao;
import com._S2JG.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import com._S2JG.model.dao.LoginDao;
import com._S2JG.model.dao.MemberDao;
import com._S2JG.model.dto.LoginDto;
import com._S2JG.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class MemberService {
@Autowired
private MemberDao memberDao;

    // == 김건우 ============================================================== //
@Autowired
private LoginDao loginDao;


    // == 회원가입 처리 요청 =================== //
    public boolean doPostSignup( MemberDto memberDto ){
        System.out.println("MemberService.doPostSignup");
        System.out.println("memberDto = " + memberDto);
        return memberDao.doPostSignup( memberDto );
    }
    // == 회원가입 처리 요청 END =================== //

    // == 회원가입 아이디 중복 체크 요청 =================== //
    public boolean doGetFindIdCheck( String memberId ){
        System.out.println("MemberService.doGetFindIdCheck");
        System.out.println("memberId = " + memberId);
        return memberDao.doGetFindIdCheck( memberId );
    }
    // == 회원가입 아이디 중복 체크 요청 END =================== //

    // == 회원가입 전화번호 중복 체크 요청 =================== //
    public boolean doGetFindPhoneCheck( String memberPhone ){
        System.out.println("MemberService.doGetFindPhoneCheck");
        System.out.println("memberPhone = " + memberPhone);
        return memberDao.doGetFindPhoneCheck( memberPhone );
    }
    // == 회원가입 전화번호 중복 체크 요청 END =================== //

    public LoginDto doGetLoginInfo(String memberid ){
        System.out.println("MemberService.doGetLoginInfo");
        // 1. DAO 호출
        return loginDao.doGetLoginInfo( memberid );
    }

    // == 회원가입 이메일 중복 체크 요청 =================== //

    public boolean doGetFindEmailCheck( String memberEmail ){
        System.out.println("MemberService.doGetFindEmailCheck");
        System.out.println("memberEmail = " + memberEmail);

        return memberDao.doGetFindEmailCheck( memberEmail );
    }
    // == 회원가입 이메일 중복 체크 요청 END =================== //
    // == 김건우 ============================================================== //
}
