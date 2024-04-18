package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserBoardDto {

    long bno;
    String btitle;
    String bcontent;
    String bfile;
    long bview;
    String bdate;
    long memberNo;

}
