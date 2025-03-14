package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.ProductDataDto;
import com.myshop.internetshop.classes.services.ProductDataService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ProductDataDto getProductDataById(@PathVariable int id) {
        return productDataService.getProductDataById(id);
    }
}
