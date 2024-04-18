package com._S2JG.model.dao;

import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.model.dto.RouteTimeDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao extends Dao{


    public List<ReservationDto> doGetReservation( long memberNo ){
        System.out.println("UserDao.doGetReservation");
        System.out.println("memberNo = " + memberNo);
        List<ReservationDto> list = new ArrayList<>();
        ReservationDto reservationDto = null;
        try {
            String sql = "select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo where r.memberNo = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1 , memberNo);
            rs = ps.executeQuery();
            while ( rs.next() ){
                System.out.println("memberNo = " + memberNo);
                 reservationDto = ReservationDto.builder()
                         .reservationNo( rs.getInt("reservationNo"))
                         .reservationSeatNum( rs.getInt("reservationSeatNum"))
                         .reservationPrice( rs.getInt("reservationPrice"))
                         .memberName( rs.getString("memberName"))
                         .reservationStatus( rs.getInt("reservationStatus"))
                         .reservationDate( rs.getString("reservationDate"))
                         .routeStartTerminal( rs.getString("routeStartTerminal"))
                         .routeEndTerminal( rs.getString("routeEndTerminal"))
                         .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                         .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                         .memberNo( rs.getLong("memberNo"))
                         .build();
                 list.add(reservationDto);
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return list;
    }

    // 2. 승객 예약 삭제 기능

    public boolean doUpdateReservation( ReservationDto reservationDto ){
        System.out.println("UserDao.doUpdateReservation");
        System.out.println("reservationDto = " + reservationDto);
        try {
            String sql = "update reservation set reservationStatus = 1 where memberNo = ? and reservationNo = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1 , reservationDto.getMemberNo());
            ps.setInt(2 , reservationDto.getReservationNo());
            int count = ps.executeUpdate();
            if ( count == 1 ) return true;
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 3. 승객 예약 취소 기능
    public boolean doDeleteReservation( long memberNo , int reservationNo ){
        System.out.println("UserController.doDeleteReservation");
        System.out.println("memberNo = " + memberNo + ", reservationNo = " + reservationNo);
        try {
            String sql = "delete from reservation where memberNo = ? and reservationNo = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1 , memberNo);
            ps.setInt(2 , reservationNo);
            int count = ps.executeUpdate();
            if ( count == 1 ) return true;
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }


        return false;
    }
     // 2. 노선 검색 기능
    public List<RouteTimeDto> doGetRouteSearch( String key , String value ) { // 그 각각 뭐지 이 기능을 넣고 싶어요 출발지에도 도착지에도 둘다 이게 조건을 쓴건데
        System.out.println("UserDao.doGetRouteSearch");
        System.out.println("key = " + key + ", value = " + value);
        List<RouteTimeDto> list = new ArrayList<>();
        try {
            String sql = "select * from routeTime rt inner join bus b on rt.busNo = b.busNo inner join route r on rt.routeNo = r.routeNo inner join busClass bc on b.busClassNo = bc.busClassNo";
            if (!value.isEmpty()) {
                if (key.equals("routeStartTerminal")) {
                    sql += " where (routeStartTerminal like '%" + value + "%' and routeTimeStatus = 0) or (routeStartTerminal like '%" + value + "%' and routeTimeStatus = 1)";
                } else if (key.equals("routeEndTerminal")) {
                    sql += " where (routeEndTerminal like '%" + value + "%' and routeTimeStatus = 1) or (routeEndTerminal like '%" + value + "%' and routeTimeStatus = 0)";
                }
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    RouteTimeDto routeTimeDto = RouteTimeDto.builder()
                            .routeStartTerminal(rs.getString("routeStartTerminal"))
                            .routeEndTerminal(rs.getString("routeEndTerminal"))
                            .routeTimeStatus(rs.getInt("routeTimeStatus"))
                            .routeTimeNo(rs.getInt("routeTimeNo"))
                            .build();
                    list.add(routeTimeDto);
                }
            }
        }catch(Exception e ) {
            System.out.println("e = " + e);
        }
            return list;
    }
    // 2-1. 출발지 검색 기능
    public List<RouteDto> doGetStartRouteSearch(String routeEndTerminal){
        System.out.println("다오UserDao.doGetRouteSearch");
        List<RouteDto> list = new ArrayList<>();
        try {
            String sql = "select distinct routeStartTerminal from route";
            if(!routeEndTerminal.equals(" ")){
                sql += " where routeEndTerminal = '"+routeEndTerminal+"'";
            }
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while ( rs.next() ){
                RouteDto routeDto = RouteDto.builder()
                        .routeStartTerminal( rs.getString("routeStartTerminal"))
                        .build();
                        list.add( routeDto);
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return list;

    }

    // 2-2. 도착지 검색 기능
    public List<RouteDto> doGetEndRouteSearch(String routeStartTerminal){
        System.out.println("컨트롤UserController.doGetRouteSearch");
        List<RouteDto> list = new ArrayList<>();
        try {
            String sql = "select distinct routeEndTerminal from route";
            if(!routeStartTerminal.equals(" ")){
                sql += " where routeStartTerminal = '" + routeStartTerminal  +"'";
            }
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while ( rs.next() ){
                RouteDto routeDto = RouteDto.builder()
                        .routeEndTerminal( rs.getString("routeEndTerminal"))
                        .build();
                list.add( routeDto);
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);
        }
        return list; // 여기
    }

    // 6. 게시물 작성자 인증
    public boolean ReservationWriterAuth( long rno , String mid ){
        try {
            String sql = "select * from reservation r inner join member m on r.memberNo = m.memberNo where r.reservationNo = ? and m.memberId = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong( 1 , rno);
            ps.setString(2 , mid );
            rs = ps.executeQuery();
            if ( rs.next() ){
                return true;
            }
        }catch ( Exception e ){
            System.out.println("e = " + e);

        }
        return false;
    }


}
