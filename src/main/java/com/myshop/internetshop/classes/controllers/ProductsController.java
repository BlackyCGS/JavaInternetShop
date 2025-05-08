package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Manage products data")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Operation(summary = "Get all products")
    @GetMapping("")
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productsService.getAllProducts(pageable);
    }

    @Operation(summary = "Get gpus using parameters. If parameter is null, it "
            + "doesnt involved in search")
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
                                            defaultValue = "-1") Integer vram,
                                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productsService.getGpuByParams(producer, boostClock, displayPort, hdmi,
                tdp, vram, pageable);
    }

    @GetMapping("/category/motherboard")
    public List<ProductDto> getMotherboards(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
            ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productsService.getMotherboards(pageable);
    }

    @Operation(summary = "Get product data by id")
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") int id) {
        return new ProductDto(productsService.getProductById(id));
    }

    @Operation(summary = "Add product")
    @PostMapping()
    public ProductDto addProduct(@Valid @RequestBody ProductDto product) {
        return productsService.saveProduct(product);
    }

    @Operation(summary = "Add multiple products")
    @PostMapping("/list")
    public List<ProductDto> addProducts(@Valid @RequestBody List<ProductDto> products) {
        return productsService.saveProducts(products);
    }

    @Operation(summary = "Clear products cache")
    @PostMapping("/clearCache")
    void clearCache() {
        productsService.clearCache();
    }

    @Operation(summary = "delete all")
    @DeleteMapping
    ResponseEntity<String> deleteAllProducts() {
        productsService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") long id) {
        productsService.deleteProductById(id);
    }

    @Operation(summary = "Get total number of products")
    @GetMapping("/amount")
    public ResponseEntity<Integer> getTotalProducts(
            @RequestParam(required = true) String category,
            @RequestParam(required = false, defaultValue = "") String name
    ) {
        return ResponseEntity.ok(productsService.getProductsCount(category, name));
    }

    @Operation(summary = "Modify a product")
    @PutMapping("/")
    ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto product) {
        return ResponseEntity.ok(productsService.updateProduct(product));
    }

    @Operation(summary = "find by name")
    @GetMapping("/name")
    public ResponseEntity<List<ProductDto>> findProductByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(productsService.searchByName(name, pageable));
    }
}
