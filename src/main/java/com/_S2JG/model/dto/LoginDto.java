package com._S2JG.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginDto {

    private long memberNo;
    private String memberid;
    private String memberpw;
    private String memberName;

    private int memberGrant;

}
