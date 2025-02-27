package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.ProductsDto;
import com.myshop.internetshop.classes.services.ProductsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/all")
    public List<ProductsDto> getAllProducts() {
        return productsService.getAllProducts();
    }
}
