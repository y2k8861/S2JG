package com._S2JG.model.dao;

import com._S2JG.model.dto.BoardDto;
import com._S2JG.service.UserBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBoardDao extends Dao {


    //글쓰기 처리
    public long doPostBoaruserdWrite(BoardDto boardDto){
        System.out.println("BoardDao.doPostBoardWrite");

        System.out.println("boardDto = " + boardDto);

        try{
            String sql = "insert into board (btitle , bcontent , memberNo ) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setLong(3,boardDto.getMemberNo());

            int count = ps.executeUpdate();
            if(count == 1){
            return 1;

            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;

    }
    //전체 글 출력 호출

    public List<BoardDto> doGetBoardViewList(int startRow , int pageBoardsize, int bcno, String Key, String value){
        System.out.println("BoardDao.doGetBoardViewList");
        BoardDto boardDto = null;
        List<BoardDto> list = new ArrayList<>();

        try{
            String sql = "select * from board b inner join member m on b.memberNo= m.memberNo";
            if(bcno > 0) { sql += " where bcno ="+bcno ;}

            if( !value.isEmpty()){
                System.out.println("검색 키워드가 존재");
                if(bcno > 0){ sql += " and " ;} //카테고리가 있을때. and로 연결
                else{sql += " where " ;}        // 카테고리 없을때 where 로 연결
                sql += Key+" like '%"+value+"%' ";
            }

            sql += " order by b.bdate desc limit ? , ? ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardsize);


            rs = ps.executeQuery();
            while (rs.next()){
                boardDto = new BoardDto(rs.getLong("bno"),rs.getString("btitle"),rs.getString("bcontent"),rs.getString("bfile"),rs.getLong("bview"),rs.getString("bdate"),rs.getLong("memberNo"));
                list.add(boardDto);
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }

    //2-2 전체 게시물 수 호출
    public int getBoardSize(int bcno ,String Key , String value){
        try {
            String sql = "select count(*) from board b inner join member m on b.memberNo = m.memberNo ";
            //==============1.만약에 카테고리 조건이 있으면 where 추가
            if(bcno > 0){sql += " where b.bcno = "+bcno ;}
            //===============2. 만약에 검색 있을때
            if( !value.isEmpty()){
                System.out.println("검색 키워드가 존재");
                if(bcno > 0){ sql += " and " ;} //카테고리가 있을때. and로 연결
                else{sql += " where " ;}        // 카테고리 없을때 where 로 연결
                sql += Key+" like '%"+value+"%' ";
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }



    //3-2 개별 글 출력시 조회수 증가
    public void boardViewIncress(int bno){
        try{
            String sql = "update board set bview = bview + 1 where bno ="+bno;
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return;
        }catch (Exception e){
            System.out.println("e = " + e);
        }
    }

    //3-1개별 글 출력 호출
    public BoardDto doGetBoardView( int bno){
        System.out.println("BoardDao.doGetBoardView");
        BoardDto boardDto = null;
        try{
            String sql = "select * from board b inner join member m on b.memberNo = m.memberNo where b.bno = ?";
            ps = conn.prepareStatement(sql);
            System.out.println("bno = " + bno);
            ps.setLong(1,bno);
            rs = ps.executeQuery();
            if(rs.next()){
                boardDto = new BoardDto(rs.getLong("bno"),rs.getString("btitle"),rs.getString("bcontent"),rs.getString("bfile"),rs.getLong("bview"),rs.getString("bdate"),rs.getLong("memberNo"));
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }

        return boardDto;
    }

    //글 수정 처리
    public boolean doUpdateBoard( BoardDto boardDto){
        System.out.println("BoardDao.doUpdateBoard");
        System.out.println("boardDto = " + boardDto);
        try {
            String sql = "update board set btitle = ?, bcontent = ? where bno = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, boardDto.getBtitle());
            ps.setString(2, boardDto.getBcontent());
            ps.setLong(3,boardDto.getBno());

            int count = ps.executeUpdate(); if(count == 1) {
                System.out.println("count = " + count);
                return true; }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }


    //5.글 삭제 처리
    public boolean doDeleteBoard( int bno ){

        try{
            String sql = "delete from board where bno = "+bno;
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate();
            if(count == 1) return true;


        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return  false;
    }






}
