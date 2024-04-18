package com._S2JG.model.dao;

import com._S2JG.model.dto.LoginDto;
import org.springframework.stereotype.Component;

@Component
public class LoginDao extends Dao {

    public boolean doPostLogin(LoginDto loginDto){
        try {
            String sql = "select * from member where memberId = ? and memberPw = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, loginDto.getMemberid());
            ps.setString(2, loginDto.getMemberpw());
            rs = ps.executeQuery();
            if(rs.next()){
                return true;

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }


    public LoginDto doGetLoginInfo( String memberid ){
        System.out.println("LoginDao.doGetLoginInfo");
        System.out.println("memberid = " + memberid);

        LoginDto loginDto = null;
        try{
            String sql = "select * from member where memberid = ? ";
            ps = conn.prepareStatement( sql );
            ps.setString( 1 , memberid );
            rs = ps.executeQuery();
            if( rs.next() ) {
                loginDto = new LoginDto(rs.getLong("memberNo"),rs.getString("memberid"),
                                        rs.getString("memberpw"),rs.getString("memberName"), rs.getInt("memberGrant"));
            }

        }catch (Exception e ){  System.out.println("e = " + e);}
        return loginDto;
    }




}
