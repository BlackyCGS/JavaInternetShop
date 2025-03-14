package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Product findById(Integer productId);
    void deleteById(int id);
}
