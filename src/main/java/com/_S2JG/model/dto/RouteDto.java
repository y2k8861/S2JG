package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RouteDto {
    private long routeNo;
    private int routePrice;
    private String routeStartTerminal;
    private String routeEndTerminal;
    private String routeDate;
}
