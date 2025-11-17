package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.entities.Review;
import com.myshop.internetshop.classes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    void deleteByProductAndUser(Product product, User user);

    Review findByProductAndUser(Product product, User user);

    boolean existsByProductAndUser(Product product, User user);

}
