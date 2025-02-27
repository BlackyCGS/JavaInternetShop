package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.ProductsDto;
import com.myshop.internetshop.classes.entities.Products;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    public final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<ProductsDto> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        List<ProductsDto> productsDtos = new ArrayList<>();
        for (Products product : products) {
            ProductsDto productsDto = convertToDto(product);
            productsDtos.add(productsDto);
        }
        return productsDtos;
    }

    private ProductsDto convertToDto(Products products) {
        return new ProductsDto(products.getProductId(), products.getName(), products.getPrice());
    }

}
