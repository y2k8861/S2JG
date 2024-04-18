package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InquiryDto {
    private int ino; // 문의게시판 번호
    private String ipw; // 비회원제 때 쓸 비밀번호
    private String ititle; // 문의게시판 제목
    private String icontent; // 문의게시판 내용
    private String idate; // 문의게시판 글등록날짜
    private byte istatus; // 답변완료 체크
    private long memberNo; // 로그인한 상태에서 쓴 회원의 번호
    private String memberId; // 로그인한 상태에서 쓴 회원의 아이디
    private String memberPw; // // 로그인한 상태에서 쓴 회원의 비밀번호
    private String memberPhone; // 관리자만 볼수있는 회원의 전화번호
    private int memberGrant; // 회원등급
}
