package com.myshop.internetshop.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HddFilter {
    Float minPrice;
    Float maxPrice;
    Integer minSize;
    Integer maxSize;
}
