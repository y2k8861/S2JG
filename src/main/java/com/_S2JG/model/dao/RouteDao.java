package com._S2JG.model.dao;

import com._S2JG.model.dto.RouteDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RouteDao extends Dao {
    // 기능 요청
    // 1. 노선 등록 요청
    public boolean doPostRoute(List<Map<String,Object>> list){
        System.out.println("RouteDao.doPostRoute");
        System.out.println("list = " + list);
        try {
            for(Map<String,Object> map : list){
                String sql = "insert into route(routePrice,routeStartTerminal,routeEndTerminal) values(?,?,?)";
                ps = conn.prepareCall(sql);
                ps.setInt(1,(Integer) map.get("routePrice"));
                ps.setString(2,(String) map.get("routeStartTerminal"));
                ps.setString(3,(String) map.get("routeEndTerminal"));
                ps.executeUpdate();
            }
            return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public List<Object> doGetAllRoute(){
        System.out.println("RouteService.doGetAllRoute");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from route order by routeNo desc";
            ps = conn.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                RouteDto routeDto = new RouteDto(
                        rs.getLong("routeNo"),
                        rs.getInt("routePrice"),
                        rs.getString("routeStartTerminal"),
                        rs.getString("routeEndTerminal"),
                        rs.getString("routeDate")
                );
                list.add(routeDto);
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return list;
    }

    // 2. 노선 전체 불러오기 요청
    public List<Object> doGetAllRoute(int pageBoardSize, int startRow, String key, String keyword){
        System.out.println("RouteService.doGetAllRoute");
        List<Object> list = new ArrayList<>();

        try {
            String sql = "select * from route";
            if(!keyword.isEmpty()) sql += " where " + key + " like '%"+ keyword + "%'";
            sql += " order by routeNo desc limit ?,?";
            ps = conn.prepareCall(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs = ps.executeQuery();
            while (rs.next()){
                RouteDto routeDto = new RouteDto(
                        rs.getLong("routeNo"),
                        rs.getInt("routePrice"),
                        rs.getString("routeStartTerminal"),
                        rs.getString("routeEndTerminal"),
                        rs.getString("routeDate")
                );
                list.add(routeDto);
            }
        } catch (Exception e){
            System.out.println(" e = " + e);
        }

        return list;
    }

    // 2-2. 노선 전체 게시물 수
    public int getRouteSize(String key,String keyword){
        try {
            String sql = "select count(*) from route";
            if(!keyword.isEmpty())sql += " where " + key + " like '%"+ keyword + "%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){return rs.getInt(1);}
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 3. 노선 개별 불러오기 요청
    public RouteDto doGetviewRoute( long routeNo){
        System.out.println("RouteDao.doGetviewRoute");
        RouteDto routeDto = new RouteDto();
        try {
            String sql = "select * from route where routeNo = " + routeNo;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                routeDto.setRouteStartTerminal(rs.getString("routeStartTerminal"));
                routeDto.setRouteEndTerminal(rs.getString("routeEndTerminal"));
                routeDto.setRoutePrice(rs.getInt("routePrice"));
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return routeDto;
    }

    // 4. 노선 수정 요청
    public boolean doUpdateRoute(RouteDto routeDto){
        System.out.println("routeDto = " + routeDto);
        try {
            String sql = "update route set routePrice = ?,routeStartTerminal = ?, routeEndTerminal = ? where routeNo =  ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,routeDto.getRoutePrice());
            ps.setString(2,routeDto.getRouteStartTerminal());
            ps.setString(3,routeDto.getRouteEndTerminal());
            ps.setLong(4,routeDto.getRouteNo());
            int cout = ps.executeUpdate();
            if(cout == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 5. 노선 삭제 요청
    public boolean doDeleteRoute(long[] list){
        System.out.println("list = " + list);
        try {
            String sql = "delete from route where routeNo = ?";
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
}
