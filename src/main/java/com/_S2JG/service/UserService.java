package com._S2JG.service;

import com._S2JG.model.dao.UserDao;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.model.dto.RouteTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<ReservationDto> doGetReservation(long memberNo){
        System.out.println("UserService.doGetReservation");
        System.out.println("memberNo = " + memberNo);


        return userDao.doGetReservation( memberNo );
    }

    // 2. 승객 예약 수정 기능

    public boolean doUpdateReservation( ReservationDto reservationDto ){
        System.out.println("UserService.doUpdateReservation");
        System.out.println("reservationDto = " + reservationDto);

        return userDao.doUpdateReservation( reservationDto );
    }

    // 3. 승객 예약 취소 기능
    public boolean doDeleteReservation( long memberNo , int reservationNo){
        System.out.println("UserController.doDeleteReservation");
        System.out.println("memberNo = " + memberNo + ", reservationNo = " + reservationNo);

        return userDao.doDeleteReservation( memberNo , reservationNo );
    }
    // 2. 노선 검색 기능

    public List<RouteTimeDto> doGetRouteSearch(String key , String value ){
        System.out.println("UserService.doGetRouteSearch");
        System.out.println("key = " + key + ", value = " + value);

        return userDao.doGetRouteSearch( key , value );
    }
    // 2-1. 출발지 선택/검색 기능
    public List<RouteDto> doGetStartRouteSearch(String routeEndTerminal){
        System.out.println("서비스UserService.doGetRouteSearch");

        return userDao.doGetStartRouteSearch(routeEndTerminal);
}

    // 2-2. 도착지 검색 기능
    public List<RouteDto> doGetEndRouteSearch(String routeStartTerminal){
        System.out.println("컨트롤UserController.doGetRouteSearch");

        return userDao.doGetEndRouteSearch(routeStartTerminal);
    }


    // 6. 게시물 작성자 인증
    public boolean ReservationWriterAuth( long rno , String mid ){

        return userDao.ReservationWriterAuth( rno , mid );
    }

}
