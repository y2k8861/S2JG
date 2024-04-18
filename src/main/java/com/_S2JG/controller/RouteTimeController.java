package com._S2JG.controller;

import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.model.dto.RouteTimeDto;
import com._S2JG.service.FileService;
import com._S2JG.service.MemberService;
import com._S2JG.service.ReservationService;
import com._S2JG.service.RouteService;
import com._S2JG.service.RouteTimeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class RouteTimeController {
    @Autowired
    private RouteTimeService routeTimeService;
    @Autowired
    private FileService fileService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReservationService reservationService;

    //승객 시간 sql 불러오는 함수
    @GetMapping("/route/time.do")
    @ResponseBody
    public PageDto doGetSeatTime( @RequestParam int page ,@RequestParam int pageBoardSize ,
                                  @RequestParam String key, @RequestParam String keyword,
                                  @RequestParam long routeNo){
        System.out.println("RouteTimeController.doGetSeatTime");
        return routeTimeService.doGetSeatTime(page,pageBoardSize,key,keyword,routeNo);

    }
    //화면 보여주는 페이지
//    @GetMapping("/route/time")
//    public String doPostSeatTime(){
//        System.out.println("routeTimeService = " + routeTimeService);
//        return "/bustimepage";
//    }

    @GetMapping("/route/view.do")
    @ResponseBody
    public List<RouteTimeDto> doGetAllRoutetime(){
        System.out.println("RouteController.doGetAllRoute");
        return routeTimeService.doGetAllRoutetime();
    }


    // 좌석예매 페이지
    @GetMapping("/route/time/reserve")
    public String doGetreservePage(){
        System.out.println("RouteTimeController.doGetreserve");

        return "/busseat";
    }

    @GetMapping("/route/time/reserve.do")
    @ResponseBody
    public String doGetreserve(@RequestParam long routeTimeNo){
        System.out.println("RouteTimeController.doGetreserve");

        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return "login"; // 세션없다(로그인 안했다.)

        return routeTimeService.doGetRoutetimeDto(routeTimeNo).getBusClassName();
    }
    //좌석 예메 후 등록해주는 메소드
    @PostMapping("/route/time/doReserve")
    @ResponseBody
    public boolean doPostreserve(@RequestParam int seat, @RequestParam long routeTimeNo){

        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return false; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();
        int routePrice = routeTimeService.doGetRoutetimeDto(routeTimeNo).getRoutePrice();
        int busClassPrice = routeTimeService.doGetRoutetimeDto(routeTimeNo).getBusClassPrice();

        ReservationDto reservationDto = ReservationDto.builder()
                .reservationSeatNum(seat)
                .reservationStatus(0)
                .reservationPrice( routePrice + busClassPrice )
                .memberNo(memberNo)
                .routeTimeNo(routeTimeNo)
                .build();

        return reservationService.doPostreserve(reservationDto);
    }

    @GetMapping("/route/time/block")
    @ResponseBody
    public List<Integer> seatBlock(@RequestParam long routeTimeNo){

        List<Integer> list = reservationService.seatBlock(routeTimeNo);
        return list;
    }

//    @PostMapping("/route/time/reserveCreate")
//    public String doPostreserveCreate(){
//        return "/busseat";
//    }

        @PostMapping("/route/time/routeNo")
        @ResponseBody
        public long doPostRouteNo(@RequestParam String startSelectBox, @RequestParam String endSelectBox){
            System.out.println("RouteTimeController.doPostRouteNo 실행");

            long routeNo = routeTimeService.doPostRouteNo(startSelectBox, endSelectBox);
            return routeNo;
        }





    // 노선별 시간 등록 요청
    @PostMapping("/admin/routeTime/create")
    @ResponseBody
    public boolean doPostRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("RouteTimeController.doPostRouteTime");
        System.out.println("routeTimeDto = " + routeTimeDto);
        return routeTimeService.doPostRouteTime(routeTimeDto);
    }

    // 1-2 노선별 시간 파일 등록
    @PostMapping("/admin/routeTime/fileUpload")
    @ResponseBody
    public boolean doUploadBus(@RequestParam(value="excelFile") MultipartFile file){
        System.out.println("BusController.doUploadBus 실행");
        if(file.isEmpty()) { return false; }
        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return false; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();
        List<Map<String,Object>> list = fileService.doUploadFile(memberNo, file , 3);
        if(list == null){return false;}

        return routeTimeService.doPostRoute(list);
    }

    // 노선별 시간 파일 다운
    @GetMapping("/admin/routeTime/fileDownload")
    @ResponseBody
    public boolean doDownloadFile(){
        System.out.println("BusController.doDownloadBus 실행");

        long memberNo = 0;
        System.out.println("memberNo = " + memberNo);

        return fileService.doDownloadExel(memberNo,3);
    }

    // 노선별 시간 전체 불러오기 요청
    @GetMapping("/admin/routeTime/all")
    @ResponseBody
    public PageDto doGetAllRouteTime(
            @RequestParam int page,
            @RequestParam int pageBoardSize,
            @RequestParam String key,
            @RequestParam String keyword
    ){
        System.out.println("RouteTimeController.doGetAllRouteTime");
        return routeTimeService.doGetAllRouteTime(page,pageBoardSize,key,keyword);
    }

    // 노선별 시간 개별 불러오기 요청
    @GetMapping("/admin/routeTime/view.do")
    @ResponseBody
    public RouteTimeDto doGetviewRoute(@RequestParam long routeTimeNo){
        System.out.println("RouteTimeController.doGetAllRouteTime");
        return routeTimeService.doGetviewRouteTime(routeTimeNo);
    }

    // 노선별 시간 수정 요청
    @PutMapping("/admin/routeTime/update.do")
    @ResponseBody
    public boolean doUpdateRouteTime(RouteTimeDto routeTimeDto){
        System.out.println("routeDto = " + routeTimeDto);
        return routeTimeService.doUpdateRouteTime(routeTimeDto);
    }

    // 노선별 시간 삭제 요청
    @DeleteMapping("/admin/routeTime/delete.do")
    @ResponseBody
    public boolean doDeleteRouteTime(@RequestParam(value="listCheck[]") long[] listCheck){
        System.out.println("listCheck = " + listCheck);
        return routeTimeService.doDeleteRouteTime(listCheck);
    }

    // 노선별 시간 등록 페이지 요청
    @GetMapping("/admin/routeTime/create")
    public String doGetRoutTimePage(){
        return "/admin/routeTimeCreate";
    }

    // 노선별 시간 전체 페이지 요청
    @GetMapping("/admin/routeTime")
    public String doGetAllRoutTimePage(){
        return "/admin/routeTime";
    }

    // 노선별 시간 개별&수정 페이지 요청
    @GetMapping("/admin/routeTime/view")
    public String doGetRoutViewPage(){
        return "/admin/routeTimeCreate";
    }



}
