package com._S2JG.controller;

import com._S2JG.model.dao.BoardDao;
import com._S2JG.model.dto.BoardDto;
import com._S2JG.model.dto.BoardPageDto;
import com._S2JG.service.BoardService;
import com._S2JG.service.MemberService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;


    //글쓰기 페이지 이동
    @GetMapping("/admin/write")
    public String getboardWrite(){
        System.out.println("BoardController.getboardWrite");

        return "/admin/write";
    }


    //글쓰기 처리
    @PostMapping("/write.do")
    @ResponseBody
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoardWrite");

        //1. 현재 로그인된 세션(톰캣서버(자바프로그램)메모리 저장소) 호출
        Object object = request.getSession().getAttribute("loginDto");
        if(object == null)return -2;

        String mid = (String) object;

        long mno = memberService.doGetLoginInfo(mid).getMemberNo();

        boardDto.setMemberNo(mno);

        return boardService.doPostBoardWrite(boardDto);
    }

    @GetMapping("/do")
    @ResponseBody
    public BoardPageDto doGetBoardViewList(@RequestParam int page , @RequestParam int pageBoardSize, @RequestParam int bcno , @RequestParam("key")String Key, @RequestParam("keyword")String value){
        System.out.println("BoardController.doGetBoardViewList");
        System.out.println("Key = " + Key);
        return  boardService.doGetBoardViewList( page , pageBoardSize , bcno , Key, value );
    }

    //공지사항 페이지 이동
    @GetMapping("")
    public String getBoard(){
        return "/board";
    }


    //공지사항 개별글
    @GetMapping("/view.do")
    @ResponseBody
    public BoardDto doGetBoardView(@RequestParam int bno){
        System.out.println("BoardController.doGetBoardView.do");
        return boardService.doGetBoardView(bno);
    }
    //공지사항 개별글 상세페이지
    @GetMapping("/view")
    public String getBoardView(int bno){
        System.out.println("BoardController.getBoardView");
        return "/view";
    }

    //4. 글 수정 처리
    @PutMapping("/update.do")
    @ResponseBody
    public boolean doUpdateBoard( BoardDto boardDto){
        System.out.println("BoardController.doUpdateBoard");
        System.out.println("boardDto = " + boardDto);
            return boardService.doUpdateBoard(boardDto);


    }
    //4. 글수정 페이지 이동
    @GetMapping("/update")

    public String getBoardUpdate(){
        System.out.println("BoardController.getBoardUpdate");
        return "/admin/update";
    }




    //5. 글 삭제 처리
    @DeleteMapping("/delete.do")
    @ResponseBody
    public boolean doDeleteBoard(@RequestParam int bno ){
        System.out.println("BoardController.doDeleteBoard");

        // 현재 수정할 게시물의 작성자 아이디
        return boardService.doDeleteBoard(bno);
    }



}
