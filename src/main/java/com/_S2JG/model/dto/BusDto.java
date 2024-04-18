package com._S2JG.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class BusDto {
    long busNo;
    String busNumber;
    long busClassNo;
    long memberNo;
    String busDate;
    String busPosition;
}
