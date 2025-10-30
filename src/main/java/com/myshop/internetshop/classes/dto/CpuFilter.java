package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CpuFilter {
    Float minPrice;
    Float maxPrice;
    Integer minCores;
    Integer maxCores;
    Integer minThreads;
    Integer maxThreads;
    Integer minTdp;
    Integer maxTdp;
    String socket;
}
