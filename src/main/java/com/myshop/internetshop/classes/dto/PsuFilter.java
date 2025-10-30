package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PsuFilter {

    Float minPrice;
    Float maxPrice;
    Integer minWatt;
    Integer maxWatt;
    String size;
    String efficiencyRating;
}
