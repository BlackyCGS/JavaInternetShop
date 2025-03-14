package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.services.ProductsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("")
    public List<ProductDto> getAllProducts() {
        return productsService.getAllProducts();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        productsService.deleteProductById(id);
    }
}
