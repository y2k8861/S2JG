package com._S2JG.controller;

import com._S2JG.model.dto.BusDto;
import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.RouteDto;
import com._S2JG.service.FileService;
import com._S2JG.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/route")
public class RouteController {

    @Autowired
    RouteService routeService;

    @Autowired
    FileService fileService;

    // 기능 요청
    // 1. 노선 등록 요청
    @PostMapping("/create")
    @ResponseBody
    public boolean doPostRoute(RouteDto routeDto){
        System.out.println("RouteController.doPostRoute");
        System.out.println("list = " + routeDto);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("routePrice",routeDto.getRoutePrice());
        map.put("routeStartTerminal",routeDto.getRouteStartTerminal());
        map.put("routeEndTerminal",routeDto.getRouteEndTerminal());
        list.add(map);
        return routeService.doPostRoute(list);
    }

    // 1-2 노선 파일 등록
    @PostMapping("/fileUpload")
    @ResponseBody
    public boolean doUploadBus(@RequestParam(value="excelFile")MultipartFile file){
        System.out.println("BusController.doUploadBus 실행");
        if(file.isEmpty()) { return false; }
        long memberNo = 0;
        List<Map<String,Object>> list = fileService.doUploadFile(memberNo, file , 2);
        if(list == null){return false;}

        return routeService.doPostRoute(list);
    }

    // 노선 파일 다운
    @GetMapping("/fileDownload")
    @ResponseBody
    public boolean doDownloadFile(){
        System.out.println("BusController.doDownloadBus 실행");

        long memberNo = 0;
        System.out.println("memberNo = " + memberNo);

        return fileService.doDownloadExel(memberNo,2);
    }

    // 2. 노선 전체 불러오기 요청
    @GetMapping("/all.do")
    @ResponseBody
    public List<Object> doGetAllRoute(){
        System.out.println("RouteController.doGetAllRoute");
        return routeService.doGetAllRoute();
    }


    // 2. 노선 전체 불러오기 요청
    @GetMapping("/all")
    @ResponseBody
    public PageDto doGetAllRoute(
            @RequestParam int page,
            @RequestParam int pageBoardSize,
            @RequestParam String key,
            @RequestParam String keyword
    ){
        System.out.println("RouteController.doGetAllRoute");
        return routeService.doGetAllRoute(page,pageBoardSize,key,keyword);
    }

    // 3. 노선 개별 불러오기 요청
    @GetMapping("/view.do")
    @ResponseBody
    public RouteDto doGetviewRoute(@RequestParam long routeNo){
        System.out.println("RouteController.doGetAllRoute");
        return routeService.doGetviewRoute(routeNo);
    }

    // 4. 노선 수정 요청
    @PutMapping("/update.do")
    @ResponseBody
    public boolean doUpdateRoute(RouteDto routeDto){
        System.out.println("routeDto = " + routeDto);
        return routeService.doUpdateRoute(routeDto);
    }

    // 5. 노선 삭제 요청
    @DeleteMapping("/delete.do")
    @ResponseBody
    public boolean doDeleteRoute(@RequestParam(value="listCheck[]") long[] listCheck){
        System.out.println("listCheck = " + listCheck);
        return routeService.doDeleteRoute(listCheck);
    }


    // 페이지 요청 =============================================================== //
    // 1. 노선 등록 페이지 요청
    @GetMapping("/create")
    public String doGetRoutPage(){
        return "/admin/routeCreate";
    }

    // 2. 노선 전체 페이지 요청
    @GetMapping("")
    public String doGetAllRoutPage(){
        return "/admin/route";
    }

    // 3. 노선 개별&수정 페이지 요청
    @GetMapping("/view")
    public String doGetRoutViewPage(){
        return "/admin/routeCreate";
    }
}
