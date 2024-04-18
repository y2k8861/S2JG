package com._S2JG.model.dto;


import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberDto {
   private long memberNo;
   private String memberId;
   private String memberPw;
   private String memberName;
   private String memberEmail;
   private String memberPhone;
   private int memberGrant;
   private String memberdate;

}
