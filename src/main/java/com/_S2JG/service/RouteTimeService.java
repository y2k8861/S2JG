package com._S2JG.service;

import com._S2JG.model.dao.ReservationDao;
import com._S2JG.model.dao.RouteTimeDao;
import com._S2JG.model.dto.BusDto;
import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class RouteTimeService {

    @Autowired
    private RouteTimeDao routeTimeDao;
    @Autowired
    private ReservationDao reservationDao;


    public PageDto doGetSeatTime(int page , int pageBoardSize , String key, String keyword, long routeNo){
        System.out.println("RouteTimeService.doGetSeatTime");
        int startRow = (page-1)*pageBoardSize;
        // 3. 총 페이지 구하기
        // 1. 전체 게시물 수
        int totalPage = routeTimeDao.getReservationSize(routeNo,key,keyword);
        int totalBoardSize = totalPage % pageBoardSize == 0 ?
                totalPage / pageBoardSize :
                totalPage / pageBoardSize + 1 ;
        // 2. 총 페이지 수 계산
        List<Object> list =  routeTimeDao.doGetSeatTime(startRow,pageBoardSize,key,keyword,routeNo);

        // 5. 페이징 버튼 개수
        // 1. 페이지 버튼 최대 개수
        int btnSize = 10 ;    // 5개씩
        // 2. 페이지 버튼시작번호
        int startBtn = ((page-1)/btnSize*btnSize)+1;
        // 3. 페이지 끝 번호
        int endBtn = startBtn+btnSize-1;
        // 만약에 페이지 버튼의 끝번호가 총페이지 보다는 커질수 없다.
        if(endBtn >=totalBoardSize) endBtn = totalBoardSize;

        // pageDto 구성 * 빌더패턴 : 생성자의 단점(매개변수에 따른 유연성 부족) 을 보안
        PageDto pageDto = new PageDto(page,totalPage,startBtn,endBtn,totalBoardSize,list);

       return pageDto;
    }

    public List<RouteTimeDto> doGetAllRoutetime(){
        System.out.println("RouteService.doGetAllRoute");
        return routeTimeDao.doGetAllRoutetime();
    }

    //좌석 예메 후 등록해주는 메소드
    public boolean doPostreserve(ReservationDto reservationDto, long memberNo, int reservationSeatNum, int routeNo, int reservationprice){
        System.out.println("RouteTimeService.doPostreserve");
        System.out.println("reservationDto = " + reservationDto);
        return routeTimeDao.doPostreserve(reservationDto,memberNo,reservationSeatNum,routeNo,reservationprice);
    }

    public long doPostRouteNo(String startSelectBox, String endSelectBox){

        return routeTimeDao.doPostRouteNo(startSelectBox, endSelectBox);
    }


    // routTimeNo를 가져와서 객체에 담기 **필요하면 필드 추가
    public RouteTimeDto doGetRoutetimeDto(long routeTimeNo){

        return routeTimeDao.doGetRoutetimeDto(routeTimeNo);
    }












    // 노선별 시간 등록 요청
    public boolean doPostRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("RouteTimeController.doPostRouteTime");
        String routeTimeStartTime = routeTimeDto.getRouteTimeStartTime();
        String routeTimeEndTime = routeTimeDto.getRouteTimeEndTime();
        routeTimeDto.setRouteTimeStartTime(routeTimeStartTime.split("T")[0] + " " + routeTimeStartTime.split("T")[1] + ":00" );
        routeTimeDto.setRouteTimeEndTime(routeTimeEndTime.split("T")[0] + " " + routeTimeEndTime.split("T")[1] + ":00" );
        System.out.println("routeTimeDto = " + routeTimeDto);
        return routeTimeDao.doPostRouteTime(routeTimeDto);
    }

    public boolean doPostRoute(List<Map<String,Object>> list){
        System.out.println("RouteService.doPostRoute");
        System.out.println("list = " + list);
        return routeTimeDao.doPostRouteTime(list);
    }

    // 노선별 시간 전체 불러오기 요청
    public List<Object> doGetAllRouteTime(){
        System.out.println("RouteTimeController.doGetAllRouteTime");
        return routeTimeDao.doGetAllRouteTime();
    }

    public PageDto doGetAllRouteTime(int page,int pageBoardSize, String key, String keyword){
        System.out.println("RouteService.doGetAllRoute");
        int startRow = (page-1)*pageBoardSize;
        // 3. 총 페이지 구하기
        // 1. 전체 게시물 수
        int totalPage = routeTimeDao.getRouteTimeSize(key,keyword);
        int totalBoardSize = (totalPage-1)/pageBoardSize +1;
        // 2. 총 페이지 수 계산
        List<Object> list =  routeTimeDao.doGetAllRouteTime(pageBoardSize,startRow,key,keyword);

        // 5. 페이징 버튼 개수
        // 1. 페이지 버튼 최대 개수
        int btnSize = 5 ;    // 5개씩
        // 2. 페이지 버튼시작번호
        int startBtn = ((page-1)/btnSize*btnSize)+1;
        // 3. 페이지 끝 번호
        int endBtn = startBtn+btnSize-1;
        // 만약에 페이지 버튼의 끝번호가 총페이지 보다는 커질수 없다.
        if(endBtn >=totalBoardSize) endBtn = totalBoardSize;

        // pageDto 구성 * 빌더패턴 : 생성자의 단점(매개변수에 따른 유연성 부족) 을 보안
        PageDto pageDto = new PageDto(page,totalPage,startBtn,endBtn,totalBoardSize,list);

//        BoardPageDto boardPageDto = new BoardPageDto(page,totalBoardSize,startBtn,endtBtn,list);
        return pageDto;
    }

    // 노선별 시간 개별 불러오기 요청
    public RouteTimeDto doGetviewRouteTime(long routeTimeNo){
        System.out.println("RouteTimeController.doGetAllRouteTime");
        return routeTimeDao.doGetviewRouteTime(routeTimeNo);
    }

    // 노선별 시간 수정 요청
    public boolean doUpdateRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("routeDto = " + routeTimeDto);
        String routeTimeStartTime = routeTimeDto.getRouteTimeStartTime();
        String routeTimeEndTime = routeTimeDto.getRouteTimeEndTime();
        routeTimeDto.setRouteTimeStartTime(routeTimeStartTime.split("T")[0] + " " + routeTimeStartTime.split("T")[1] + ":00" );
        routeTimeDto.setRouteTimeEndTime(routeTimeEndTime.split("T")[0] + " " + routeTimeEndTime.split("T")[1] + ":00" );
        return routeTimeDao.doUpdateRouteTime(routeTimeDto);
    }

    // 노선별 시간 삭제 요청
    public boolean doDeleteRouteTime(long[] listCheck){
        System.out.println("listCheck = " + listCheck);
        return routeTimeDao.doDeleteRouteTime(listCheck);
    }
}
