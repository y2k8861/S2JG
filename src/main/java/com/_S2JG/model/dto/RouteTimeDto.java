package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RouteTimeDto {
    private long routeTimeNo;
    private String routeTimeStartTime;
    private String routeTimeEndTime;
    private int routeTimeStatus;
    private int busNo;
    private int routeNo;
    private String routeTimeDate;

    private String routeStartTerminal;
    private String routeEndTerminal;
    private int routePrice;
    private int busClassPrice;
    private String busClassName;
    private int busClassSeat;
}
