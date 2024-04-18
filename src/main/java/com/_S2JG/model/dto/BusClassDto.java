package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class BusClassDto {
    long busClassNo;
    String busClassName;
    int busClassSeat;
    int busClassPrice;
    String busClassDate;
}
