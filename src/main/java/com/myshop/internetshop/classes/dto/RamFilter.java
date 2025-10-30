package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RamFilter {
    Float minPrice;
    Float maxPrice;
    Integer minSize;
    Integer maxSize;
    String ramType;
    String timings;
}
