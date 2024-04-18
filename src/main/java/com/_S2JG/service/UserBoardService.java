package com._S2JG.service;

import com._S2JG.model.dao.BoardDao;
import com._S2JG.model.dao.UserBoardDao;
import com._S2JG.model.dto.BoardDto;
import com._S2JG.model.dto.BoardPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Service
public class UserBoardService {

    @Autowired
    private UserBoardDao userBoardDao;



    //전체 글 출력 호출
    public BoardPageDto doGetBoarduserViewList( int page , int pageBoardSize ,int bcno, String Key ,String value){
        System.out.println("BoardService.doGetBoardViewList");
        System.out.println("page = " + page + ", pageBoardSize = " + pageBoardSize + ", bcno = " + bcno + ", Key = " + Key + ", value = " + value);
        //페이지처리시 사용할 sql 구문 : limit 시작레코드번호(0부터5), 출력개수

        //1.페이지당 게시물을 출력할 개수
        //  int pageBoardsize = 2;

        //2. 페이지당 게시물을 출력할 시작 레코드 번호.
        int startRow = (page-1) * pageBoardSize;

        //3. 총 페이지 수
        //1. 전체게시물수
        int totalBoardSize = userBoardDao.getBoardSize( bcno , Key, value );
        //2. 총 페이지수 계산
        int totalPage = totalBoardSize % pageBoardSize == 0 ?
                totalBoardSize / pageBoardSize :
                totalBoardSize / pageBoardSize + 1 ;

        //4. 게시물 정보 요청
        List<BoardDto> list = userBoardDao.doGetBoardViewList( startRow,pageBoardSize , bcno , Key , value);

        int btnSize = 5;        //5개씩
        //2. 페이지 버튼 시작번호
        int startBtn = (page-1)/btnSize*btnSize+1; ;      //1번 버튼
        //3. 페이지버튼 끝 번호
        int endBtn = startBtn + btnSize-1;  //5번 버튼
        // 만약에 페이지버튼의 끝번호가 총페이지수 보다는 커질수 없다
        if(endBtn >= totalPage) endBtn = totalPage;



        // page Dto 구성 * 빌더 패넡 : 생성자의 단점(매개변수에 따른 유연성 부족)을 보완
        // 사용방법 : 클래스명.builder(). 필드명(대입값).필드명(대입값).build();
        // + 생성자 보완 수정
        BoardPageDto boardPageDto = BoardPageDto.builder().page(page).totalBoardSize(totalBoardSize).totalPage(totalPage).list(list).startBtn(startBtn).endBtn(endBtn).build();



        return boardPageDto;

    }


    public BoardDto doGetBoardView(@RequestParam int bno){
        System.out.println("BoardService.doGetBoardView.do");
        return userBoardDao.doGetBoardView(bno);
    }


    public String getBoardView(int bno){
        return "/board/view";
    }


    //4. 글 수정 처리
    public boolean doUpdateBoard( BoardDto boardDto){
        System.out.println("BoardService.doUpdateBoard");
        return userBoardDao.doUpdateBoard(boardDto);
    }


    //5. 글 삭제 처리
    public boolean doDeleteBoard( int bno ){
        System.out.println("BoardController.doDeleteBoard");
        //- 레코드 삭제 하기전에 삭제할 첨부파일명을 임시로 꺼내둔다.


        return userBoardDao.doDeleteBoard(bno);
    }





}
