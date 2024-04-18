package com._S2JG.model.dao;

import com._S2JG.model.dto.InquiryDto;
import com._S2JG.model.dto.PageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class InquiryDao extends Dao{

    public boolean doPostInquiryWrite(InquiryDto inquiryDto){
        System.out.println("InquiryDao.doPostInquiryWrite");
        System.out.println("inquiryDto = " + inquiryDto);
        try {
            String sql = "insert into inquiry( ipw , ititle , icontent , memberNo ) values ( ? , ? , ? , ? )";
            ps = conn.prepareStatement( sql );
            ps.setString(1 , inquiryDto.getIpw());
            ps.setString(2 , inquiryDto.getItitle());
            ps.setString(3 , inquiryDto.getIcontent());
            ps.setLong(4 , inquiryDto.getMemberNo());
            int count = ps.executeUpdate();
            if ( count == 1 ) return true;
        }catch ( Exception e ){
            System.out.println("문의하기SQL오류 : " + e);
        }
        return false;
    }

    public List<Object> doGetInquiryView(int startRow , int pageBoardSize, String key, String value ){
        System.out.println("하하 InquiryDao.doGetInquiryView");
        System.out.println("page = " + startRow + ", pageBoardSize = " + pageBoardSize + ", key = " + key + ", value = " + value);
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from inquiry";

            sql += " order by ino desc limit ? , ? ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1 , startRow);
            ps.setInt(2 , pageBoardSize);


            rs = ps.executeQuery();
            while ( rs.next() ){
                InquiryDto inquiryDto = InquiryDto.builder()
                        .ino( rs.getInt("ino"))
                        .ititle( rs.getString("ititle"))
                        .icontent( rs.getString("icontent"))
                        .istatus( rs.getByte("istatus"))
                        .idate( rs.getString("idate"))
                        .memberNo( rs.getLong("memberNo"))
                        .build();
                        list.add(inquiryDto);
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return list;
    }

    // 2-2. 예약 전체 게시물 수
    public int getInquirySize(String key , String value){
        try {
            String sql = "select count(*) from inquiry";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e){
            System.out.println("오류뜬곳은 여기~");
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 개별글 조회
    public InquiryDto doGetInquiryInoView( int ino ){
        System.out.println("InquiryDao.doGetInquiryInoView");
        System.out.println("ino = " + ino);
        InquiryDto inquiryDto = null;
        try {
            String sql = "select * from inquiry i inner join member m on i.memberNo = m.memberNo where i.ino=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,ino);
            rs = ps.executeQuery();
            if( rs.next() ){
                        inquiryDto = InquiryDto.builder()
                        .ipw( rs.getString("ipw"))
                        .memberId( rs.getString("memberId"))
                        .memberPw( rs.getString("memberPw"))
                        .ititle( rs.getString("ititle"))
                        .icontent( rs.getString("icontent"))
                        .ino( rs.getInt("ino"))
                        .idate( rs.getString("idate"))
                        .istatus( rs.getByte("istatus"))
                        .memberNo( rs.getLong("memberNo"))
                        .memberPhone( rs.getString("memberPhone"))
                        .memberGrant( rs.getInt("memberGrant"))
                        .build();
            }
        }catch ( Exception e ){
            System.out.println("개별글 출력 다오 = " + e);
        }
        System.out.println("inquiryDto 방금막뽑아온 = " + inquiryDto);
        return inquiryDto;
    }


    // 4. 문의 함 글 수정
    public boolean doPutInquiryUpdate( InquiryDto inquiryDto ){
        System.out.println("문의함수정InquiryController.doPutInquiryUpdate");
        System.out.println("inquiryDto = " + inquiryDto);
        try {
            String sql = "update inquiry set ititle = ? , icontent = ?  where ino = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inquiryDto.getItitle()); // 첫 번째 매개변수 설정
            ps.setString(2, inquiryDto.getIcontent()); // 두 번째 매개변수 설정
            ps.setInt(3, inquiryDto.getIno()); // 세 번째 매개변수 설정
            int count = ps.executeUpdate();
            if ( count == 1 ) return true;
        }catch ( Exception e ){
            System.out.println("아니 이오류 도데체 뭔데 ;e = " + e);
        }
        return false;
    }

    // 5. 문의 함 삭제

    public boolean doDeleteInquiry( int ino ){
        System.out.println("InquiryDao.doDeleteInquiry");
        System.out.println("ino = " + ino);
        try {
            String sql = "delete from inquiry where ino = " + ino;
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate();
            if ( count == 1 ) return true;
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return false;
    }
}

