package com._S2JG.controller;

import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.ReservationDto;
import com._S2JG.service.FileService;
import com._S2JG.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FileService fileService;

    // 1. 이용현황 조회(전체)
    @GetMapping("/admin/reservation/do")
    @ResponseBody
    public PageDto dogetAdminReservation(@RequestParam int page, @RequestParam int pageBoardSize, @RequestParam String key, @RequestParam String value , @RequestParam int memberGrant){
        System.out.println("ReservationController.getAdminHistory");
        System.out.println("page = " + page + ", pageBoardSize = " + pageBoardSize + ", key = " + key + ", keyword = " + value);

        return reservationService.dogetAdminReservation( page , pageBoardSize , key , value , memberGrant );
    }

    // 파일 다운
    @GetMapping("/admin/reservation/fileDownload")
    @ResponseBody
    public boolean doDownloadFile(){
        System.out.println("BusController.doDownloadBus 실행");

        long memberNo = 0;
        System.out.println("memberNo = " + memberNo);

        return fileService.doDownloadExel(memberNo,4);
    }


    // ========= 페이지 ===================================== //

    // 1. 이용현황 페이지
    @GetMapping("/admin/reservation")
    public String getAdminHistoryView(){
        return "/admin/reservation";
    }

}
