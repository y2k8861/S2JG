package com._S2JG.controller;

import com._S2JG.model.dto.BoardDto;
import com._S2JG.model.dto.BoardPageDto;
import com._S2JG.service.BoardService;
import com._S2JG.service.MemberService;
import com._S2JG.service.UserBoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/userboard")
public class UserBoardController {
    @Autowired
    private UserBoardService userBoardService;
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


    @GetMapping("/do")
    @ResponseBody
    public BoardPageDto doGetBoarduserViewList(@RequestParam int page , @RequestParam int pageBoardSize, @RequestParam int bcno , @RequestParam("key")String Key, @RequestParam("keyword")String value){
        System.out.println("BoardController.doGetBoardViewList");
        return userBoardService.doGetBoarduserViewList( page , pageBoardSize , bcno , Key, value );
    }

    //공지사항 페이지 이동
    @GetMapping("")
    public String getBoard(){
        return "/userboard";
    }


    //개별글
    @GetMapping("/view.do")
    @ResponseBody
    public BoardDto doGetBoardView(@RequestParam int bno){
        System.out.println("BoardController.doGetBoardView.do 유저");
        return userBoardService.doGetBoardView(bno);
    }
    //개별글 상세페이지
    @GetMapping("/view")
    public String getBoardView(int bno){
        System.out.println("BoardController.getBoardView 유저");
        return "/userboardview";
    }



}
