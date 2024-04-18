package com._S2JG.model.dao;

import com._S2JG.model.dto.BusClassDto;
import com._S2JG.model.dto.BusDto;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BusDao extends Dao{

    // 1. 버스 전체 조회
    public List<Object> doGetBusViewList( long memberNo, int startRow, int pageBoardSize,
                                          int bcno, String field, String value){

        System.out.println("로그인된 아이디 : " + memberNo);
        System.out.println("field는? "+field+ "value는? "+value);
        BusDto busDto = null;
        List<Object> list = new ArrayList<>();
        try{
            String sql = "select * from bus where memberNo = ?";

            System.out.println("bcno : "+bcno);
            // 등급별 조건 추가 (프리미엄, 우등, 일반)
            if(bcno > 0){ sql += " and busClassNo = "+bcno;}

            if( !value.isEmpty() ){
                sql += " and "+field+" like '%"+value+"%' ";
            }

            sql += " limit ?, ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, memberNo);
            ps.setInt(2, startRow);
            ps.setInt(3, pageBoardSize);
            rs = ps.executeQuery();
            while(rs.next()){
                busDto = new BusDto( rs.getLong("busNo"), rs.getString("busNumber"),
                        rs.getLong("busClassNo"), rs.getLong("memberNo"),
                        rs.getString("busDate"), rs.getString("busPosition"));
                list.add(busDto);
            }

        }
        catch(Exception e){
            System.out.println("버스 전체 조회 오류 : " + e);
        }
        return list;
    }

    public List<BusDto> doGetBusViewList( BusDto busDto){
        List<BusDto> list = new ArrayList<>();
        try{
            String sql = "select * from bus where busPosition = ? and memberNo = " + busDto.getMemberNo();
            ps = conn.prepareStatement(sql);
            ps.setString(1,busDto.getBusPosition());
            rs = ps.executeQuery();
            while(rs.next()){
                BusDto dto = new BusDto(
                        rs.getLong("busNo"),
                        rs.getString("busNumber"),
                        rs.getLong("busClassNo"),
                        rs.getLong("memberNo"),
                        rs.getString("busDate"),
                        rs.getString("busPosition"));
                list.add(dto);
            }

        }
        catch(Exception e){
            System.out.println("버스 전체 조회 오류 : " + e);
        }
        return list;
    }

    // 등록된 버스 개수 반환
    public int getBusSize(long memberNo, int bcno){
        try{
            String sql = "select count(*) from bus";

            if(bcno > 0){
                sql+= " where busClassNo = "+bcno;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if( rs.next() ) {
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    // 2. 버스 개별 조회
    public BusDto doGetBusView(int busNo){
        System.out.println("Dao : 버스 개별 조회");
        BusDto busDto = null;
        try{
            String sql = "select * from bus where busNo = ?";
            ps =conn.prepareStatement(sql);
            ps.setLong( 1 , busNo );
            rs = ps.executeQuery();
            if(rs.next()){
                busDto = new BusDto( rs.getLong("busNo"), rs.getString("busNumber"),
                        rs.getLong("busClassNo"), rs.getLong("memberNo"),
                        rs.getString("busDate"), rs.getString("busPosition"));
            }
        }catch(Exception e){
            System.out.println("버스 개별 조회 오류 : "+e);
        }
        return busDto;
    }

    // 3. 버스 등록
    public boolean doCreateBus(BusDto busDto){
        try{
            System.out.println("테스트용 memberNo" + busDto.getMemberNo());
            String sql = "insert into bus(busNumber, busClassNo, memberNo, busPosition ) value(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString( 1 , busDto.getBusNumber());
            ps.setLong( 2 , busDto.getBusClassNo());
            ps.setLong( 3 , busDto.getMemberNo());
            ps.setString(4, busDto.getBusPosition());

            int count = ps.executeUpdate();
            if( count == 1 ){
                return true;
            }
        }
        catch(Exception e){
            System.out.println("버스 등록 오류 : " + e);
        }
        return false;
    }

    // 4. 버스 수정
    public boolean doUpdateBus(BusDto busDto){
        try{
            String sql = "update bus set busNumber = ? , busClassNo = ? , busPosition = ? where busNo = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString( 1 , busDto.getBusNumber());
            ps.setLong( 2 , busDto.getBusClassNo() );
            ps.setString( 3 , busDto.getBusPosition() );
            ps.setLong( 4 , busDto.getBusNo() );

            int count = ps.executeUpdate();
            if( count == 1 ){ return true; }
        }catch (Exception e ){ System.out.println("e = " + e); }
        return false;
    }

    // 5. 버스 삭제
    public boolean doDeleteBus(long[] list){
        try {
            System.out.println("삭제할 busNo????" +list);
            String sql = "delete from bus where busNo = ?";
            ps = conn.prepareStatement(sql);
            for(int i=0; i< list.length; i++){
                ps.setLong(1,list[i]);
                int count = ps.executeUpdate();
                if(count == 1){
                    System.out.println("삭제 성공");
                }
            }
            return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }

    // 파일 업로드
    public boolean doUploadBus(long memberNo, List<Map<String,Object>> list){
        System.out.println("BusDao.doUploadBus 실행");
        System.out.println("받아온 리스트는 ? " + list);
        int count = 0; int result = 0;

        System.out.println("리스트사이즈는? : "+ list.size());
        try{
            for(int i=0; i<list.size(); i++) {
                String sql = "insert into bus(busNumber,busClassNo,memberNo, busPosition) value(?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, (String) list.get(i).get("busNumber"));

                System.out.println();
                ps.setInt(2, (Integer) list.get(i).get("busClassNo"));
                ps.setLong(3, memberNo);
                ps.setString(4, (String) list.get(i).get("busPosition"));

                count = ps.executeUpdate();
                result += count;
                System.out.println("result는? : " + result);
            }
            if(result == list.size()){
                return true;
            }

        }catch (Exception e){
            System.out.println("파일업로드 DB등록 오류 : " +e);
        }
        return false;
    }

    // 파일 다운로드용 전체 출력
    public List<Object> doDownloadBus(long memberNo){
        BusDto busDto = null;
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from bus where memberNo = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, memberNo);
            rs = ps.executeQuery();
            while(rs.next()){
                busDto = new BusDto( rs.getLong("busNo"), rs.getString("busNumber"),
                        rs.getLong("busClassNo"), rs.getLong("memberNo"),
                        rs.getString("busDate"), rs.getString("busPosition"));
                list.add(busDto);
            }
        }catch (Exception e){
            System.out.println("파일 다운로드 DB오류 : " + e);
        }
        return list;
    }
}


