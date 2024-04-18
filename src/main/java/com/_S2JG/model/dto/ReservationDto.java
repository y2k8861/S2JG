package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReservationDto {
    private int reservationNo;
    private int reservationSeatNum;
    private int reservationStatus;
    private int reservationPrice;
    private long memberNo;
    private long routeTimeNo;
    private String reservationDate;
    // 추가 필드
    private String memberName;
    private String routeStartTerminal;
    private String routeEndTerminal;
    private String routeTimeStartTime;
    private String routeTimeEndTime;
}
