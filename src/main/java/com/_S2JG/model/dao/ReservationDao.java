package com._S2JG.model.dao;

import com._S2JG.model.dto.ReservationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationDao extends Dao{

    // 1. 이용현황 조회(전체)

    // 2. 예약 전체 불러오기 요청
    public List<Object> dogetAdminReservation(int startRow , int pageBoardSize , String key, String value , int memberGrant){
        System.out.println("RouteService.doGetAllRoute");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo where memberGrant = ? ";
            if(!value.isEmpty()) sql +=  key + " like '%"+ value + "%'";
            sql += " order by reservationNo desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,memberGrant);
            ps.setInt(2,startRow);
            ps.setInt(3,pageBoardSize);
            System.out.println(startRow);
            System.out.println(pageBoardSize);
            rs = ps.executeQuery();
            while (rs.next()){
                ReservationDto reservationDto = ReservationDto.builder()
                        .reservationNo(rs.getInt("reservationNo"))
                        .reservationSeatNum(rs.getInt("reservationSeatNum"))
                        .reservationStatus(rs.getInt("reservationStatus"))
                        .reservationPrice(rs.getInt("reservationPrice"))
                        .reservationDate(rs.getString("reservationDate"))
                        .routeStartTerminal(rs.getString("routeStartTerminal"))
                        .routeEndTerminal(rs.getString("routeEndTerminal"))
                        .memberName(rs.getString("memberName"))
                        .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                        .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                        .build();
                list.add(reservationDto);
            }
            System.out.println("예약디비에서 뽑아온 리스트 = " + list);
        } catch (Exception e){
            System.out.println(" e = " + e);
        }

        return list;
    }

    // 2-2. 예약 전체 게시물 수
    public int getReservationSize(String key , String value){
        try {
            String sql = "select count(*) from reservation";
            if(!value.isEmpty())sql += " where " + key + " like '%"+ value + "%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){return rs.getInt(1);}
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    public List<Object> dogetAdminReservation(){
        System.out.println("ReservationDao.doGetAllRoute");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo order by reservationNo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                ReservationDto reservationDto = ReservationDto.builder()
                        .reservationNo(rs.getInt("reservationNo"))
                        .reservationSeatNum(rs.getInt("reservationSeatNum"))
                        .reservationStatus(rs.getInt("reservationStatus"))
                        .reservationPrice(rs.getInt("reservationPrice"))
                        .reservationDate(rs.getString("reservationDate"))
                        .memberName(rs.getString("memberName"))
                        .routeStartTerminal(rs.getString("routeStartTerminal"))
                        .routeEndTerminal(rs.getString("routeEndTerminal"))
                        .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                        .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                        .build();
                list.add(reservationDto);
            }
        } catch (Exception e){
            System.out.println(" e = " + e);
        }

        return list;
    }

    // 예약 등록
    public boolean doPostreserve(ReservationDto reservationDto){

        try{
            String sql = "insert into reservation(reservationSeatNum,reservationStatus,reservationPrice,memberNo,routeTimeNo) " +
                    " value(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationDto.getReservationSeatNum());
            ps.setInt(2,reservationDto.getReservationStatus());
            ps.setInt(3, reservationDto.getReservationPrice());
            ps.setLong(4, reservationDto.getMemberNo());
            ps.setLong(5, reservationDto.getRouteTimeNo());

            int count = ps.executeUpdate();
            if( count == 1 ){
                return true;
            }

        }catch (Exception e){
            System.out.println("예약 등록 DB 오류 : " + e);
        }
        return false;
    }


    // 좌석 막기
    public List<Integer> seatBlock(long routeTimeNo){
        List<Integer> list = new ArrayList<>();
        try{
            String sql = "select reservationSeatNum from reservation where routeTimeNo= ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, routeTimeNo);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(rs.getInt("reservationSeatNum"));
            }
            System.out.println("list는???" + list);
        }catch (Exception e){
            System.out.println("DB오류" + e);
        }


        return list;
    }
}
