package com._S2JG.controller;

import com._S2JG.model.dto.MemberDto;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.model.dto.RouteTimeDto;
import com._S2JG.service.MemberService;
import com._S2JG.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    MemberService memberService;



    // 1. 예약 확인 기능
    @GetMapping("/member/reservation.do")
    @ResponseBody
    public List<ReservationDto> doGetReservation(@RequestParam long memberNo){
        System.out.println("UserController.doGetReservation");
        System.out.println("memberNo = " + memberNo);
        return userService.doGetReservation( memberNo );
    }

    // 2. 승객 예약 수정 기능
    @PutMapping("/member/reservation.doUpdate")
    @ResponseBody
    public boolean doUpdateReservation(ReservationDto reservationDto){
        System.out.println("UserController.doUpdateReservation");
        System.out.println("reservationDto = " + reservationDto);

        // 유효성검사
        // 1. 현재 로그인된 아이디 ( 세션 )
        Object object = request.getSession().getAttribute("loginDto");
        if ( object != null ){
            String mid = (String)object;
            // 해당 세션정보가 작성한 글 인지 체크
            boolean result = userService.ReservationWriterAuth( reservationDto.getReservationNo() , mid );
            if ( result ){
                // 2. 현재 수정할 게시물의 작성자 아이디( DB )
                return userService.doUpdateReservation( reservationDto );
            }
            // 2. 현재 수정할 게시물의 작성자 아이디( DB )

        }
        return false;
    }


    // 3. 승객 예약 취소 기능
    @DeleteMapping("/member/reservation.doDelete")
    @ResponseBody
    public boolean doDeleteReservation(@RequestParam long memberNo , @RequestParam int reservationNo){
        System.out.println("UserController.doDeleteReservation");
        System.out.println("memberNo = " + memberNo + ", reservationNo = " + reservationNo);

        return userService.doDeleteReservation( memberNo , reservationNo );
    }


    //  2. 노선 검색 기능 //  함수 실행조건 : 셀렉트 박스 출발지 or 도착지 선택시?  매개변수 : 출발지,도착지 : key 음음~ value : 그 출발지,도착지 값 ? 반환 값 : 음 리스트 ㅋㅋㅋㅋ   < 이거 적어주세요 >
    @GetMapping("/member/route")
    @ResponseBody
    public List<RouteTimeDto> doGetRouteSearch(@RequestParam String key , @RequestParam String value ){
        System.out.println("UserController.doGetRouteSearch");
        System.out.println("key = " + key + ", value = " + value);

        return userService.doGetRouteSearch( key , value );
    }


    // 2-1. 출발지 선택/검색 기능
    @GetMapping("/member/startroute")
    @ResponseBody
    public List<RouteDto> doGetStartRouteSearch(@RequestParam String routeEndTerminal){
        System.out.println("컨트롤UserController.doGetStartRouteSearch");

        return userService.doGetStartRouteSearch(routeEndTerminal);
    }
    // 2-2. 도착지 검색 기능
    @GetMapping("/member/endroute")
    @ResponseBody
    public List<RouteDto> doGetEndRouteSearch(@RequestParam String routeStartTerminal){
        System.out.println("컨트롤UserController.doGetEndRouteSearch");
        System.out.println("routeStartTerminal = " + routeStartTerminal);
        return userService.doGetEndRouteSearch(routeStartTerminal);
    }



    // ================================================ 페이지 요청

    // 1. 예약확인 페이지
    @GetMapping("/member/reservation")
    public String getReservationInfo(){
        return "/user";
    }

}
