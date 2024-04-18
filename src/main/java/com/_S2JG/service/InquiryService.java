package com._S2JG.service;

import com._S2JG.model.dao.InquiryDao;
import com._S2JG.model.dao.ReservationDao;
import com._S2JG.model.dto.InquiryDto;
import com._S2JG.model.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class InquiryService {
    @Autowired
    private InquiryDao inquiryDao;
    @Autowired
    ReservationDao reservationDao;

    public boolean doPostInquiryWrite(InquiryDto inquiryDto){
        System.out.println("InquiryService.doPostInquiryWrite");
        System.out.println("inquiryDto = " + inquiryDto);

        return inquiryDao.doPostInquiryWrite(inquiryDto);
    }

    public PageDto doGetInquiryView( int page,  int pageBoardSize,  String key,  String value ){
        System.out.println("InquiryService.doGetInquiryView");
        System.out.println("page = " + page + ", pageBoardSize = " + pageBoardSize + ", key = " + key + ", value = " + value);

        // 페이지당 게시물을 출력할 시작 레코드 번호
        int startRow = ( page - 1 ) * pageBoardSize;

        // 총 페이지수 = 페이지네이션을 사용할려면 구해야함
            // 총 게시물 수
        int totalInquirySize = inquiryDao.getInquirySize( key , value );
            // 총 페이지수 계산
        int totalPage = totalInquirySize % pageBoardSize == 0 ?
                totalInquirySize / pageBoardSize :
                ( totalInquirySize / pageBoardSize )+1;
        List<Object> list = inquiryDao.doGetInquiryView( startRow , pageBoardSize , key , value );

        // 페이징 버튼 개수
            // 페이지 버튼 최대 가수
        int btnSzie = 5;
            // 페이지 버튼 시작번호
        int startBtn = ( ( page-1 ) / btnSzie ) + 1;
            // 페이지 버튼 끝번호
        int endBtn = startBtn + btnSzie - 1;

        if( endBtn >= totalPage ){
            endBtn = totalPage;
        }

        PageDto pageDto = PageDto.builder()
                .page( page )
                .totalBoardSize( totalInquirySize )
                .list( list )
                .startBtn( startBtn )
                .endBtn( endBtn )
                .totalPage( totalPage )
                .build();

        System.out.println("pageDto = " + pageDto);
        return pageDto;

    }

    // 3. 문의 함 개별 조회
    public InquiryDto doGetInquiryInoView( int ino ){
        System.out.println("InquiryService.doGetInquiryInoView");
        System.out.println("ino = " + ino);

        return inquiryDao.doGetInquiryInoView( ino );
    }

    // 4. 문의 함 글 수정
    public boolean doPutInquiryUpdate( InquiryDto inquiryDto ){
        System.out.println("문의함수정InquiryController.doPutInquiryUpdate");
        System.out.println("inquiryDto = " + inquiryDto);

        return inquiryDao.doPutInquiryUpdate( inquiryDto );
    }

    // 5. 문의 함 삭제

    public boolean doDeleteInquiry( int ino ){
        System.out.println("InquiryService.doDeleteInquiry");
        System.out.println("ino = " + ino);
        return inquiryDao.doDeleteInquiry( ino );
    }
}
