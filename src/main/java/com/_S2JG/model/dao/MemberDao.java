package com._S2JG.model.dao;

import com._S2JG.model.dao.Dao;
import com._S2JG.model.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class MemberDao extends Dao {

    // == 김건우 ============================================================== //

    // == 회원가입 처리 요청 =================== //
    public boolean doPostSignup( MemberDto memberDto ){
        System.out.println("MemberDao.doPostSignup");
        System.out.println("memberDto = " + memberDto);
        System.out.println( memberDto.getMemberId() );
        try {
            String sql = "insert into member( memberId , memberPw , memberName , memberEmail , memberPhone , memberGrant) values( ? , ? , ? , ? , ? , ? )";

            ps = conn.prepareStatement(sql);
            ps.setString(1 , memberDto.getMemberId());
            ps.setString(2 , memberDto.getMemberPw());
            ps.setString(4 , memberDto.getMemberEmail());
            ps.setString(3 , memberDto.getMemberName());
            ps.setString(5 , memberDto.getMemberPhone());
            ps.setInt(6,1);

            int count = ps.executeUpdate();

            if ( count == 1 ){ return true;}

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }
    // == 회원가입 처리 요청 END =================== //

    // == 회원가입 아이디 중복 체크 요청 =================== //
    public boolean doGetFindIdCheck( String memberId ){
        try {
            String sql = "select * from member where memberId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1 , memberId);
            rs = ps.executeQuery();
            if ( rs.next() ){
                return true;
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return false;
    }
    // == 회원가입 아이디 중복 체크 요청 END =================== //

    // == 회원가입 전화번호 중복 체크 요청 =================== //
    public boolean doGetFindPhoneCheck( String memberPhone ){
        try {
            String sql = "select * from member where memberPhone = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1 , memberPhone);
            rs = ps.executeQuery();
            if ( rs.next() ){
                return true;
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return false;
    }
    // == 회원가입 전화번호 중복 체크 요청 END =================== //

    // == 회원가입 이메일 중복 체크 요청 =================== //
    public boolean doGetFindEmailCheck( String memberEmail ){
        try {
            String sql = "select * from member where memberEmail = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1 , memberEmail);
            rs = ps.executeQuery();
            if ( rs.next() ){
                return true;
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return false;
    }
    // == 회원가입 이메일 중복 체크 요청 END =================== //

    // == 김건우 ============================================================== //


}
