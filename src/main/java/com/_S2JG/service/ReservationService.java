package com._S2JG.service;

import com._S2JG.model.dao.ReservationDao;
import com._S2JG.model.dto.PageDto;
import com._S2JG.model.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationDao reservationDao;
    // 1. 이용현황 조회(전체)

    public PageDto dogetAdminReservation( int page , int pageBoardSize , String key , String value , int memberGrant){
        System.out.println("BoardService.doGetBoardViewList");

        // 페이지 처리시 사용할 SQL 구문 : limit 시작레코드번호(0부터) , 출력개수

        // 1. 페이지당 게시물을 출력할 개수  [ 출력개수 ]
        // int pageBoardSize = 5;

        // 2. 페이지당 게시물을 출력할 시작 레코드번호    [ 시작레코드번호(0부터) ]
        int startRow = ( page - 1 ) * pageBoardSize;

        // 3. 총 페이지수 ( 페이지네이션 사용할 페이지버튼 만들려고 총페이지 수 구하기 )
        // 3-1 전체 게시물수
        int totalBoardSize = reservationDao.getReservationSize( key, value );
        // 3-2 총 페이지수 계산 ( 나머지값이 존재하면 +1 아니면 그냥 진행 )
        int totalPage = totalBoardSize % pageBoardSize == 0 ?
                totalBoardSize / pageBoardSize :
                (totalBoardSize / pageBoardSize)+1 ;
        // 4. 게시물 정보 요청
        List<Object> list = reservationDao.dogetAdminReservation( startRow , pageBoardSize , key , value , memberGrant );


        // 5. 페이징 버튼 개수
        // 5-1 페이지버튼 최대 개수
        int btnSize = 5;        // 5개씩
        // 5-2 페이지버튼 시작번호
        int startBtn = ( (page-1) / btnSize ) * btnSize + 1;
        // 5-3 페이지버튼 끝번호
        int endBtn = startBtn + btnSize - 1;
        // 만약에 페이지 버튼의 끝번호가 총페이지 보다는 커질수 없다.
        if ( endBtn >= totalPage ) {
            endBtn = totalPage;
        }

        // pageDto 구성 * 빌더패턴 : 생성자의 단점( 매개변수에 따른 유연성 부족 ) 을 보완

//        BoardPageDto boardPageDto = new BoardPageDto(
//                page , totalPage , startBtn , endBtn , list ,
//                );
        // VS
        // new 연산자 없이 builder() 함수 이용한 객체 생성 라이브러리 제공
        // 사용방법 : 클래스명.builder().필드명(대입값).필드명(대입값).build();
        // * 생성자 보단 유연성 UP : 매개변수의 순서 와 개수 자유롭다
        PageDto pageDto = PageDto.builder()
                .page( page )
                .totalBoardSize( totalBoardSize )
                .list( list )
                .startBtn( startBtn )
                .endBtn( endBtn )
                .totalPage( totalPage )
                .build();
        // VS
//        BoardPageDto boardPageDto = new  BoardPageDto();
//        boardPageDto.setPage(page);
//        boardPageDto.setTotalBoardSize(totalBoardSize);
        return pageDto;
    }

//    // 2. 이용현황 조회(날짜별)
//
//    public List<ReservationDto> getAdminHistoryDate(ReservationDto reservationDto ){
//        System.out.println("ReservationService.getAdminHistoryDate");
//        System.out.println("reservationDto = " + reservationDto);
//
//        return reservationDao.getAdminHistoryDate( reservationDto );
//    }



    // 예약 등록
    public boolean doPostreserve(ReservationDto reservationDto){

        return reservationDao.doPostreserve(reservationDto);
    }

    // 좌석 막기
    public List<Integer> seatBlock(long routeTimeNo){
        return reservationDao.seatBlock(routeTimeNo);
    }
}
