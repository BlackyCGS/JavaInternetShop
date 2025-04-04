package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.services.ProductsService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Manage products data")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Operation(description = "Get all products")
    @GetMapping("")
    public List<ProductDto> getAllProducts() {
        return productsService.getAllProducts();
    }

    @Operation(description = "Get gpus using parameters. If parameter is null, it " +
            "doesnt involved in search")
    @GetMapping("/category/gpu")
    public List<ProductDto> getGpus(@RequestParam(required = false) String producer,
                                    @RequestParam(required = false,
                                            defaultValue = "-1") Integer boostClock,
                                    @RequestParam(required = false,
                                            defaultValue = "-1") Integer displayPort,
                                    @RequestParam(required = false,
                                            defaultValue = "-1") Integer hdmi,
                                    @RequestParam(required = false,
                                            defaultValue = "-1") Integer tdp,
                                    @RequestParam(required = false,
                                            defaultValue = "-1") Integer vram) {
        return productsService.getGpuByParams(producer, boostClock, displayPort, hdmi, tdp, vram);
    }

    @Operation(description = "Delete product by id")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") long id) {
        productsService.deleteProductById(id);
    }

    @Operation(description = "Add gpu to database")
    @PostMapping("/gpu")
    public ProductDto createGpu(@RequestBody GpuRequest gpu) {
        return productsService.saveGpu(gpu);
    }

    @Operation(description = "Add multiple gpus at once")
    @PostMapping("/gpu/list")
    public ResponseEntity<String> createGpus(@RequestBody List<GpuRequest> gpus) {
        for (GpuRequest gpu : gpus) {
            productsService.saveGpu(gpu);
        }
        return ResponseEntity.ok("Gpus created");
    }

    @Operation(description = "Clear products cache")
    @GetMapping("/clearCache")
    void clearCache() {
        productsService.clearCache();
    }

    @Operation(description = "Get product data by id")
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") int id) {
        return new ProductDto(productsService.getProductById(id));
    }
}
