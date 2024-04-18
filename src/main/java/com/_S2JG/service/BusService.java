package com._S2JG.service;

import com._S2JG.model.dao.BusDao;
import com._S2JG.model.dto.BusDto;
import com._S2JG.model.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BusService {

    @Autowired
    private BusDao busDao;

    // 테스트용
    // int memberNo = 1;


    // 버스 전체 조회
    public PageDto doGetBusViewList(long memberNo, int page, int pageBoardSize, int bcno, String field, String value){

        int startRow = ( page-1 ) * pageBoardSize;

        int totalBoardSize = busDao.getBusSize( memberNo, bcno );
        System.out.println("totalBoardSize : " + totalBoardSize);

        int totalPage = totalBoardSize % pageBoardSize == 0 ?
                totalBoardSize / pageBoardSize :
                totalBoardSize / pageBoardSize + 1 ;
        List<Object> list = busDao.doGetBusViewList(memberNo, startRow, pageBoardSize, bcno, field, value);
        System.out.println("리스트테스트List : " + list);

        int btnSize = 10;
        int startBtn = (page-1)/btnSize*btnSize+1;
        int endBtn = startBtn + btnSize - 1;
        if( endBtn >= totalPage ) endBtn = totalPage;

        PageDto pageDto = PageDto.builder()
                .page( page )
                .totalBoardSize( totalBoardSize )
                .totalPage( totalPage )
                .list( list )
                .startBtn( startBtn )
                .endBtn( endBtn )
                .build();
        return pageDto;
    }

    // 버스 전체 전체 조회
    public List<BusDto> doGetBusViewList(BusDto busDto){
        return busDao.doGetBusViewList(busDto);
    };

    // 개별 버스 조회
    public BusDto doGetBusView(int busNo){
        System.out.println("BusService.doGetBusView 실행");
        return busDao.doGetBusView(busNo);
    }

    // 버스 등록
    public boolean doCreateBus(BusDto busDto){
        System.out.println("BusService.doInsertBus 실행");
        return busDao.doCreateBus(busDto);
    }

    public boolean doUploadBus(long memberNo,List<Map<String,Object>> list){
        System.out.println("BusService.doInsertBus 실행");
        return busDao.doUploadBus(memberNo,list);
    }

    // 버스 수정
    public boolean doUpdateBus(BusDto busDto){
        System.out.println("BusService.doUpdateBus 실행");
        return busDao.doUpdateBus(busDto);
    }

    // 버스 삭제
    public boolean doDeleteBus(long[] list){
        System.out.println("list = " + list);
        return busDao.doDeleteBus(list);
    }
}


