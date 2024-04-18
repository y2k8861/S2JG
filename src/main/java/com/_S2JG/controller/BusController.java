package com._S2JG.controller;

import com._S2JG.model.dto.BusDto;
import com._S2JG.model.dto.PageDto;
import com._S2JG.service.BusService;
import com._S2JG.service.FileService;
import com._S2JG.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/bus")
public class BusController {

    @Autowired
    private BusService busService;
    @Autowired
    private FileService fileService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;


    // 전체 버스 출력 호출
    @GetMapping("/do")
    @ResponseBody
    public PageDto doGetBusViewList(
            @RequestParam int page, @RequestParam int pageBoardSize,
            @RequestParam int bcno,
            @RequestParam("key") String field, @RequestParam("keyword") String value){
        System.out.println("전체 버스 출력 호출 실행");

        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return null; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();

        return busService.doGetBusViewList(memberNo, page , pageBoardSize, bcno, field, value);
    }

    // 노선 버스 출력 호출
    @GetMapping("/position.do")
    @ResponseBody
    public List<BusDto> doGetBusViewList(@RequestParam String busPosition){
        System.out.println("전체 버스 출력 호출 실행");

        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return null; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();

        BusDto busDto = BusDto.builder()
                .busPosition(busPosition)
                .memberNo(memberNo)
                .build();

        return busService.doGetBusViewList(busDto);
    }

    // 개별 버스 출력 호출
    @GetMapping("/read.do")
    @ResponseBody
    public BusDto doGetBusView(@RequestParam int busNo){
        System.out.println("개별 버스 출력 호출 실행");
        return busService.doGetBusView(busNo);
    }

    // 버스 등록 호출
    @PostMapping("/create.do")
    @ResponseBody
    public boolean doCreateBus(BusDto busDto){
        System.out.println("버스 등록 실행");

        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return false; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();
        busDto.setMemberNo(memberNo);

        return busService.doCreateBus(busDto);
    }

    // 버스 수정 호출
    @GetMapping("/update.do")
    @ResponseBody
    public BusDto getUpdateBus(@RequestParam int busNo){
        System.out.println("버스 수정 호출");
        return busService.doGetBusView(busNo);
    }

    // 버스 수정
    @PutMapping("/doUpdate")
    @ResponseBody
    public boolean doUpdateBus(BusDto busDto){
        System.out.println("버스 수정 실행");
        return busService.doUpdateBus(busDto);
    }

    // 버스 삭제 요청
    @DeleteMapping("/delete.do")
    @ResponseBody
    public boolean doDeleteBus(@RequestParam(value="listCheck[]") long[] listCheck){
        System.out.println("listCheck = " + listCheck);
        return busService.doDeleteBus(listCheck);
    }

    // 엑셀 업로드
    @PostMapping("/upload")
    @ResponseBody
    public boolean doUploadBus(@RequestParam(value="excelFile")MultipartFile file){
        System.out.println("BusController.doUploadBus 실행");
        if(file.isEmpty()) { return false; }

        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return false; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();
        List<Map<String,Object>> list = fileService.doUploadFile(memberNo, file , 1);
        if(list == null){return false;}

        return busService.doUploadBus(memberNo,list);
    }

    @GetMapping("/download")
    @ResponseBody
    public boolean doDownloadBus(){
        System.out.println("BusController.doDownloadBus 실행");

        // 멤버넘버 가져오기
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null ) return false; // 세션없다(로그인 안했다.)
        String mid = (String) object;
        long memberNo = memberService.doGetLoginInfo( mid ).getMemberNo();

        return fileService.doDownloadExel(memberNo,1);
    }

    // =============================================================== //

    // 1. 버스 전체 조회 페이지 이동
    @GetMapping("/")
    public String getBus(){
        System.out.println("버스 전체 조회 페이지");
        return "/admin/bus";
    }

    // 버스 등록 페이지 이동
    @GetMapping("/create")
    public String getCreateBus(){
        System.out.println("버스 등록 페이지");
        return "/admin/busCreate";
    }

    // 버스 개별 조회 페이지 이동
    @GetMapping("/read")
    public String getViewBus(){
        System.out.println("버스 개별 조회 페이지");
        return "/admin/busView";
    }

    // 버스 수정 페이지 이동
    @GetMapping("/update")
    public String getBusUpdate(int busNo){
        System.out.println("버스 수정 페이지");
        return "/admin/busUpdate";
    }
}
