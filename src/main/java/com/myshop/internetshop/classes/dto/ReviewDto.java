package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviewDto {
    Integer id;
    String title;
    String text;
    Integer rating;
    String username;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.text = review.getText();
        this.rating = review.getRating();
        this.username = review.getUser().getUsername();
    }
}
