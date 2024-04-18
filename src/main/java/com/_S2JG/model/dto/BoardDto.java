package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BoardDto {

    long bno;
    String btitle;
    String bcontent;
    String bfile;
    long bview;
    String bdate;
    long memberNo;

}
