package com._S2JG.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class BoardPageDto {
    private int page;
    // 총페이지 수
    private int totalPage;

    //페이지 버튼의 시작번호
    private int startBtn;
    //페이지 버튼의 끝번호
    private int endBtn;
    //총 게시물 수
    private int totalBoardSize;

    //실제 내용
    private List<BoardDto> list;
}
