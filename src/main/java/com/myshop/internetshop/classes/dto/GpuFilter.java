package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GpuFilter {
    private Float minPrice;
    private Float maxPrice;

    private Integer minBoostClock;
    private Integer maxBoostClock;

    private Integer minVram;
    private Integer maxVram;

    private Integer minTdp;
    private Integer maxTdp;
}
