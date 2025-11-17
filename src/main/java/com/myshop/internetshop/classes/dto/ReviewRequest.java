package com.myshop.internetshop.classes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class ReviewRequest {
    String title;

    String text;

    @Min(value = 0)
    @Max(value = 5)
    int rating;
}
