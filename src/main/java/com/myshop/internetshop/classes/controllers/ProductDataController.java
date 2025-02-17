package com.myshop.internetshop.classes.controllers;
import com.myshop.internetshop.classes.services.ProductDataService;
import org.springframework.web.bind.annotation.*;
import com.myshop.internetshop.classes.dto.ProductDataDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductDataController {
    private final ProductDataService productDataService;
    public ProductDataController(ProductDataService productDataService) {
        this.productDataService = productDataService;
    }
    @GetMapping("/data/{id}")
    public ProductDataDto getProductData(@PathVariable int id) {
        return productDataService.getProductData(id);
    }
    @GetMapping("/list")
    public List<ProductDataDto> getProductDatas(@RequestParam(required = false,
            defaultValue = "1") int amount) {
        return productDataService.getProductDatas(amount);
        }

    @GetMapping("/")
    public String error() {
        return "Error 404 Not Found";
    }
}
