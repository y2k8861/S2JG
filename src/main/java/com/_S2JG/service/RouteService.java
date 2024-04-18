package com._S2JG.service;

import com._S2JG.model.dao.RouteDao;
import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.RouteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Service
public class RouteService {

    @Autowired
    RouteDao routeDao;

    // 기능 요청
    // 1. 노선 등록 요청
    public boolean doPostRoute(List<Map<String,Object>> list){
        System.out.println("RouteService.doPostRoute");
        System.out.println("list = " + list);
        return routeDao.doPostRoute(list);
    }

    // 2. 노선 전체 불러오기 요청
    public List<Object> doGetAllRoute(){
        System.out.println("RouteService.doGetAllRoute");
        return routeDao.doGetAllRoute();
    }

    public PageDto doGetAllRoute(int page,int pageBoardSize, String key, String keyword){
        System.out.println("RouteService.doGetAllRoute");
        int startRow = (page-1)*pageBoardSize;
        // 3. 총 페이지 구하기
        // 1. 전체 게시물 수
        int totalPage = routeDao.getRouteSize(key,keyword);
        int totalBoardSize = (totalPage-1)/pageBoardSize +1;
        // 2. 총 페이지 수 계산
        List<Object> list =  routeDao.doGetAllRoute(pageBoardSize,startRow,key,keyword);

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


    // 3. 노선 개별 불러오기 요청
    public RouteDto doGetviewRoute( long routeNo){
        System.out.println("RouteService.doGetviewRoute");
        return routeDao.doGetviewRoute(routeNo);
    }

    // 4. 노선 수정 요청
    public boolean doUpdateRoute(RouteDto routeDto){
        System.out.println("routeDto = " + routeDto);
        return routeDao.doUpdateRoute(routeDto);
    }

    // 5. 노선 삭제 요청
    public boolean doDeleteRoute(long[] list){
        System.out.println("list = " + list);
        return routeDao.doDeleteRoute(list);
    }

    // 6. 티맵 인증
}
