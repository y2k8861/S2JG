package com._S2JG.model.dao;

import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteTimeDto;
import com._S2JG.service.RouteTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Component
public class RouteTimeDao extends Dao {


    @Autowired
    private RouteTimeService routeTimeService;




    public List<Object> doGetSeatTime( int startRow , int pageBoardSize , String key , String keyword, long routeNo ) {
        System.out.println("RouteTimeDao.doGetSeatTime");

      List<Object> list = new ArrayList<>();
        try {
            String sql = "select routeTimeNo,routeTimeStartTime,routeTimeEndTime,busClassPrice,routePrice,busClassName   \n" +
                    "from routeTime rt inner join route r on rt.routeNo = r.routeNo \n" +
                    "inner join bus b on rt.busNo=b.busNo inner join busClass bc on b.busClassNo=bc.busClassNo \n" +
                    "where rt.routeNo = ? ";
            if (!keyword.isEmpty()) sql += " and "+ key +" like '%"+ keyword + "%'";
            sql += " limit ? , ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, routeNo);
            ps.setInt(2,startRow);
            ps.setInt(3,pageBoardSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                RouteTimeDto dto = RouteTimeDto.builder()
                        .routeTimeNo(rs.getLong("routeTimeNo"))
                        .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                        .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                        .busClassName(rs.getString("busClassName"))
                        .busClassPrice(rs.getInt("busClassPrice"))
                        .routePrice(rs.getInt("routePrice"))
                        .build();
                list.add(dto);
            }
            System.out.println("list = " + list);
        } catch (Exception e) {
            System.out.println("e = " + e);

        }
        return list;
    } // do e

    // 2-2. 시간별 전체 게시물 수
    public int getReservationSize(long routeNo, String key , String keyword){
        try {
            String sql = "select count(*) from routeTime where routeNo = ?";
            if(!keyword.isEmpty())sql += " and " + key + " like '%"+ keyword + "%'";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, routeNo);
            rs = ps.executeQuery();
            if(rs.next()){return rs.getInt(1);}
        } catch (Exception e){
            System.out.println("e = " +
                    "" + e);
        }
        return 0;
    }



    public List<RouteTimeDto> doGetAllRoutetime(){
        System.out.println("RouteTimeDao.doGetAllRoutetime");
        List<RouteTimeDto> list = new ArrayList<>();
        try {
            String sql = "select * from routeTime order by routeTimeNo desc";
            ps = conn.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                RouteTimeDto routeTimeDto = new RouteTimeDto();
                list.add(routeTimeDto);
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }


    //좌석 예메 후 등록해주는 메소드
    public boolean doPostreserve(ReservationDto reservationDto, long memberNo, int reservationSeatNum, int routeNo, int reservationprice){
        System.out.println("RouteTimeDao.doPostreserve");

        try {
            System.out.println("인서트 테스트"+reservationDto.getReservationNo());
            String sql = "insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeNo) value(?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1,reservationDto.getReservationSeatNum());
            ps.setInt(2,reservationDto.getReservationStatus());
            ps.setInt(3,reservationprice);

            ps.setLong(4,memberNo);
            ps.setInt(5,routeNo);

            System.out.println("sql = " + sql);

            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            } else{
                System.out.println("Reservation insert failed"+ count);
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // routeNo 구하기
    public long doPostRouteNo(String startSelectBox, String endSelectBox){
        try{
            String sql = "select routeNo from route where routeStartTerminal = ? and routeEndTerminal = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, startSelectBox);
            ps.setString(2, endSelectBox);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("구해온 routeNo : " +  rs.getInt(1));
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("DB오류" + e);
        }
        return 0;
    }

    // routTimeNo를 가져와서 객체에 담기 **필요하면 필드 추가
    public RouteTimeDto doGetRoutetimeDto( long routeTimeNo ){
        RouteTimeDto routeTimeDto = new RouteTimeDto();
        try {
            String sql = "select routeTimeStartTime,routeTimeEndTime,busClassPrice,routePrice,busClassName\n" +
                    "from routeTime rt inner join route r on rt.routeNo = r.routeNo \n" +
                    "inner join bus b on rt.busNo=b.busNo inner join busClass bc on b.busClassNo=bc.busClassNo\n" +
                    "where rt.routeTimeNo = ?";
            ps = conn.prepareCall(sql);
            ps.setLong(1, routeTimeNo);
            rs = ps.executeQuery();
            while (rs.next()){
                routeTimeDto = RouteTimeDto.builder()
                    .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                    .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                    .busClassName(rs.getString("busClassName"))
                    .busClassPrice(rs.getInt("busClassPrice"))
                    .routePrice(rs.getInt("routePrice"))
                    .build();
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return routeTimeDto;
    }





    // 노선별 시간 등록 요청
    public boolean doPostRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("RouteTimeController.doPostRouteTime");
        System.out.println("routeTimeDto = " + routeTimeDto);
        try {
            String sql = "insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values(?,?,?,?,?)";
            ps = conn.prepareCall(sql);
            ps.setString(1,routeTimeDto.getRouteTimeStartTime());
            ps.setString(2,routeTimeDto.getRouteTimeEndTime());
            ps.setInt(3,0);
            ps.setInt(4,routeTimeDto.getBusNo());
            ps.setInt(5,routeTimeDto.getRouteNo());
            int count = ps.executeUpdate();
            if(count == 1) {
                String date_str1 = routeTimeDto.getRouteTimeStartTime();
                String date_str2 = routeTimeDto.getRouteTimeEndTime();

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = transFormat.parse(date_str1);
                Date date2 = transFormat.parse(date_str2);

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);
                cal1.add(Calendar.DATE, 1);
                cal2.add(Calendar.DATE, 1);

                ps.setString(1, transFormat.format(new Date(cal1.getTimeInMillis())));
                ps.setString(2, transFormat.format(new Date(cal2.getTimeInMillis())));
                ps.setInt(3,1);
                count = ps.executeUpdate();
                if(count == 1) return true;
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 노선별 시간 파일 등록 요청
    public boolean doPostRouteTime(List<Map<String,Object>> list){
        System.out.println("RouteDao.doPostRoute");
        System.out.println("list = " + list);
        try {
            for(Map<String,Object> map : list){
                String sql = "insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values(?,?,?,?,?)";
                ps = conn.prepareCall(sql);
                System.out.println("(String) map.get(\"routeTimeStartTime\") = " + (String) map.get("routeTimeStartTime"));
                ps.setString(1,(String) map.get("routeTimeStartTime"));
                ps.setString(2,(String) map.get("routeTimeEndTime"));
                ps.setInt(3,0);
                ps.setInt(4,(Integer) map.get("busNo"));
                ps.setInt(5,(Integer) map.get("routeNo"));
                int count = ps.executeUpdate();
                if(count == 1) {
                    String date_str1 = (String) map.get("routeTimeStartTime");
                    String date_str2 = (String) map.get("routeTimeEndTime");

                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date1 = transFormat.parse(date_str1);
                    Date date2 = transFormat.parse(date_str2);

                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(date1);
                    cal2.setTime(date2);
                    cal1.add(Calendar.DATE, 1);
                    cal2.add(Calendar.DATE, 1);

                    ps.setString(1, transFormat.format(new Date(cal1.getTimeInMillis())));
                    ps.setString(2, transFormat.format(new Date(cal2.getTimeInMillis())));
                    ps.setInt(3,1);
                    count = ps.executeUpdate();
                    if(count == 1) {}
                }
            }
            return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 노선별 시간 전체 불러오기 요청
    public List<Object> doGetAllRouteTime(){
        System.out.println("RouteTimeService.doGetAllRouteTime");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from routeTime order by routeTimeNo desc";
            ps = conn.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                RouteTimeDto routeTimeDto = new RouteTimeDto();
                routeTimeDto.setRouteTimeStartTime(rs.getString("routeTimeStartTime"));
                routeTimeDto.setRouteTimeEndTime(rs.getString("routeTimeEndTime"));
                routeTimeDto.setBusNo(rs.getInt("busNo"));
                routeTimeDto.setRouteNo(rs.getInt("routeNo"));
                routeTimeDto.setRouteTimeDate(rs.getString("routeTimeDate"));
                list.add(routeTimeDto);
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return list;
    }

    // 2. 노선 전체 불러오기 요청
    public List<Object> doGetAllRouteTime(int pageBoardSize, int startRow, String key, String keyword){
        System.out.println("RouteService.doGetAllRoute");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from routeTime rt inner join bus b on rt.busNo = b.busNo inner join route r on rt.routeNo = r.routeNo inner join busClass bc on b.busClassNo = bc.busClassNo";
            if(!keyword.isEmpty()) {
                if(key.equals("routeStartTerminal")){
                    sql += " where (routeStartTerminal like '%"+keyword +"%' and routeTimeStatus = 0) or (routeEndTerminal like '%"+keyword+"%' and routeTimeStatus = 1)";
                } else if(key.equals("routeEndTerminal")) {
                    sql += " where (routeStartTerminal like '%"+keyword +"%' and routeTimeStatus = 1) or (routeEndTerminal like '%"+keyword+"%' and routeTimeStatus = 0)";
                } else {
                    sql += " where " + key + " like '%"+ keyword + "%'";
                }
            }
            sql += " order by rt.routeTimeNo desc limit ?,?";
            System.out.println("sql = " + sql);
            ps = conn.prepareCall(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs = ps.executeQuery();
            while (rs.next()){
                RouteTimeDto routeTimeDto = RouteTimeDto.builder()
                    .routeTimeNo(rs.getLong("routeTimeNo"))
                    .routeStartTerminal(rs.getString("routeStartTerminal"))
                    .routeEndTerminal(rs.getString("routeEndTerminal"))
                    .routeTimeStatus(rs.getInt("routeTimeStatus"))
                    .routeTimeDate(rs.getString("routeTimeDate"))
                    .routeTimeStartTime(rs.getString("routeTimeStartTime"))
                    .routeTimeEndTime(rs.getString("routeTimeEndTime"))
                    .busClassPrice(rs.getInt("busClassPrice"))
                    .busClassName(rs.getString("busClassName"))
                    .routePrice(rs.getInt("routePrice"))
                .build();
                list.add(routeTimeDto);
            }
        } catch (Exception e){
            System.out.println(" e = " + e);
        }

        return list;
    }

    // 2-2. 노선 전체 게시물 수
    public int getRouteTimeSize(String key,String keyword){
        try {
            String sql = "select count(*) from routeTime";
            if(!keyword.isEmpty())sql += " where " + key + " like '%"+ keyword + "%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){return rs.getInt(1);}
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 노선별 시간 개별 불러오기 요청
    public RouteTimeDto doGetviewRouteTime(long routeTimeNo){
        System.out.println("RouteTimeController.doGetAllRouteTime");
        RouteTimeDto routeTimeDto = new RouteTimeDto();
        try {
            String sql = "select * from routeTime where routeTimeNo = " + routeTimeNo;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                routeTimeDto.setRouteTimeNo(rs.getInt("routeTimeNo"));
                routeTimeDto.setRouteNo(rs.getInt("routeNo"));
                routeTimeDto.setBusNo(rs.getInt("busNo"));
                routeTimeDto.setRouteTimeStartTime(rs.getString("routeTimeStartTime"));
                routeTimeDto.setRouteTimeEndTime(rs.getString("routeTimeEndTime"));
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return routeTimeDto;
    }

    // 노선별 시간 수정 요청
    public boolean doUpdateRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("Dao routeDto = " + routeTimeDto);
        try {
            String sql = "update routeTime set routeNo = ?,routeTimeStartTime = ?, routeTimeEndTime = ?, busNo = ? where routeTimeNo =  ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,routeTimeDto.getRouteNo());
            ps.setString(2,routeTimeDto.getRouteTimeStartTime());
            ps.setString(3,routeTimeDto.getRouteTimeEndTime());
            ps.setLong(4,routeTimeDto.getBusNo());
            ps.setLong(5,routeTimeDto.getRouteNo());
            int cout = ps.executeUpdate();
            if(cout == 1) {
                sql = "update routeTime set routeNo = ?,routeTimeStartTime = ?, routeTimeEndTime = ?, busNo = ? where routeTimeNo =  ?";
                // 날짜 1일 더하기
                LocalDate date1 = LocalDate.parse(routeTimeDto.getRouteTimeStartTime().split(" ")[0]);
                LocalDate date2 = LocalDate.parse(routeTimeDto.getRouteTimeEndTime().split(" ")[0]);
                DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fdate1 = date1.plusDays(1).format(fm) + " " + routeTimeDto.getRouteTimeStartTime().split(" ")[1] ;
                String fdate2 = date2.plusDays(1).format(fm) + " " + routeTimeDto.getRouteTimeEndTime().split(" ")[1] ;
                ps = conn.prepareStatement(sql);
                ps.setLong(1,routeTimeDto.getRouteNo());
                ps.setString(2,fdate1);
                ps.setString(3,fdate2);
                ps.setLong(4,routeTimeDto.getBusNo());
                ps.setLong(5,routeTimeDto.getRouteNo()+1);
                cout = ps.executeUpdate();
                if(cout == 1) return true;
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 노선별 시간 삭제 요청
    public boolean doDeleteRouteTime(long[] listCheck){
        System.out.println("list = " + listCheck);
        try {
            String sql = "delete from route where routeNo = ?";
            ps = conn.prepareStatement(sql);
            for(int i=0; i< listCheck.length; i++){
                ps.setLong(1,listCheck[i]);
                int count = ps.executeUpdate();
                if(count == 1){
                    System.out.println("삭제 성공");
                    ps.setLong(1,listCheck[i]+1);
                    count = ps.executeUpdate();
                    if(count == 1)System.out.println("삭제 성공");
                }
            }
            return true;

        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

} //c e
