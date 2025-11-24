package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.services.JwtService;
import com.myshop.internetshop.classes.services.ProductsService;
import com.myshop.internetshop.classes.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Manage products data")
public class ProductsController {
    private final ProductsService productsService;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public ProductsController(ProductsService productsService, JwtService jwtService,
                              UserService userService) {
        this.productsService = productsService;
        this.jwtService = jwtService;
        this.userService = userService;
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

    @Operation(summary = "Add stock to products")
    @PutMapping("/stock/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ResponseEntity<ProductDto> addStockToProduct(
            @PathVariable Integer id,
            @RequestParam Integer quantity
    ) {
        return ResponseEntity.ok(productsService.increaseStock(id, quantity));
    }


    @Operation(summary = "Get gpus using parameters. If parameter is null, it "
            + "doesnt involved in search")
    @GetMapping("/category/gpu")
    public List<ProductDto> getGpus(

                                    @RequestParam(required = false) Float minPrice,
                                    @RequestParam(required = false) Float maxPrice,
                                    @RequestParam(required = false) Integer minBoostClock,
                                    @RequestParam(required = false) Integer maxBoostClock,
                                    @RequestParam(required = false) Integer minVram,
                                    @RequestParam(required = false) Integer maxVram,
                                    @RequestParam(required = false) Integer minTdp,
                                    @RequestParam(required = false) Integer maxTdp,
                                    @RequestParam(required = false,
                                            defaultValue = "0") int pageNumber,
                                    @RequestParam(required = false,
                                            defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        GpuFilter filter = new GpuFilter(minPrice, maxPrice, minBoostClock,
                maxBoostClock, minVram, maxVram, minTdp, maxTdp);
        return productsService.gpuFilter(pageable, filter);
    }

    @GetMapping("/category/motherboard")
    public List<ProductDto> getMotherboards(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String chipset,
            @RequestParam(required = false) String formFactor,
            @RequestParam(required = false) String memoryType,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        MotherboardFilter filter = new MotherboardFilter(minPrice, maxPrice, socket,
                chipset, formFactor, memoryType);
        return productsService.motherboardFilter(pageable, filter);
    }

    @GetMapping("/category/pcCase")
    public List<ProductDto> getPCCases(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String motherboard,
            @RequestParam(required = false) String powerSupply,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        PcCaseFilter filter = new PcCaseFilter(minPrice, maxPrice, motherboard, powerSupply);
        return productsService.pcCaseFilter(pageable, filter);
    }

    @GetMapping("/category/ram")
    public List<ProductDto> getRams(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false) String ramType,
            @RequestParam(required = false) String timings,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        RamFilter filter = new RamFilter(minPrice, maxPrice, minSize, maxSize, ramType, timings);
        return productsService.ramFilter(pageable, filter);
    }

    @GetMapping("/category/cpu")
    public List<ProductDto> getCpus (
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minCores,
            @RequestParam(required = false) Integer maxCores,
            @RequestParam(required = false) Integer minThreads,
            @RequestParam(required = false) Integer maxThreads,
            @RequestParam(required = false) Integer minTdp,
            @RequestParam(required = false) Integer maxTdp,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        CpuFilter filter = new CpuFilter(minPrice, maxPrice, minCores, maxCores,
                minThreads, maxThreads, minTdp, maxTdp, socket);
        return productsService.cpuFilter(pageable, filter);
    }

    @GetMapping("/category/psu")
    public List<ProductDto> getPsus(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minWatt,
            @RequestParam(required = false) Integer maxWatt,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String efficiencyRating,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        PsuFilter filter = new PsuFilter(minPrice, maxPrice, minWatt ,maxWatt, size, efficiencyRating);
        return productsService.psuFilter(pageable, filter);
    }

    @GetMapping("category/hdd")
    public List<ProductDto> getHdds(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        HddFilter filter = new HddFilter(minPrice, maxPrice, minSize, maxSize);
        return productsService.hddFilter(pageable, filter);
    }

    @GetMapping("/category/ssd")
    public List<ProductDto> getSsds(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false) String protocol,
            @RequestParam(required = false) String formFactor,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        SsdFilter filter = new SsdFilter(minPrice, maxPrice, minSize, maxSize, protocol, formFactor);
        return productsService.ssdFilter(pageable, filter);
    }

    @Operation(summary = "Get product data by id")
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") int id) {
        return new ProductDto(productsService.getProductById(id));
    }

    @Operation(summary = "Add product")
    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public ProductDto addProduct(@Valid @RequestBody ProductDto product) {
        return productsService.saveProduct(product);
    }

    @Operation(summary = "Add multiple products")
    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    public List<ProductDto> addProducts(@Valid @RequestBody List<ProductDto> products) {
        return productsService.saveProducts(products);
    }

    @Operation(summary = "Clear products cache")
    @PostMapping("/clearCache")
    @PreAuthorize("hasRole('ADMIN')")
    void clearCache() {
        productsService.clearCache();
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") long id) {
        productsService.deleteProductById(id);
    }

    @Operation(summary = "Get total number of products")
    @GetMapping("/amount")
    public ResponseEntity<Integer> getTotalProducts(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "") String name
    ) {
        return ResponseEntity.ok(productsService.getProductsCount(category, name));
    }

    @Operation(summary = "Get total amount of gpus")
    @GetMapping("/category/gpu/amount")
    public ResponseEntity<Long> getTotalGpusFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minBoostClock,
            @RequestParam(required = false) Integer maxBoostClock,
            @RequestParam(required = false) Integer minVram,
            @RequestParam(required = false) Integer maxVram,
            @RequestParam(required = false) Integer minTdp,
            @RequestParam(required = false) Integer maxTdp
    ) {
        GpuFilter filter = new GpuFilter(minPrice, maxPrice, minBoostClock,
                maxBoostClock, minVram, maxVram, minTdp, maxTdp);
        return ResponseEntity.ok(productsService.getTotalGpusFiltered(filter));
    }

    @Operation(summary = "Get total amount of motherboards")
    @GetMapping("/category/motherboard/amount")
    public ResponseEntity<Long> getTotalMotherboardsFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String chipset,
            @RequestParam(required = false) String formFactor,
            @RequestParam(required = false) String memoryType
    ) {
        MotherboardFilter filter = new MotherboardFilter(minPrice, maxPrice, socket,
                chipset, formFactor, memoryType);
        return ResponseEntity.ok(productsService.getTotalMotherboardsFiltered(filter));
    }

    @Operation(summary = "Get total amount of motherboards")
    @GetMapping("/category/pcCase/amount")
    public ResponseEntity<Long> getTotalPcCaseFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) String motherboard,
            @RequestParam(required = false) String powerSupply
    ) {
        PcCaseFilter filter = new PcCaseFilter(minPrice, maxPrice, motherboard, powerSupply);
        return ResponseEntity.ok(productsService.getTotalPcCaseFiltered(filter));
    }

    @GetMapping("/category/ram/amount")
    public ResponseEntity<Long> getTotalRamsFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false) String ramType,
            @RequestParam(required = false) String timings
    ) {
        RamFilter filter = new RamFilter(minPrice, maxPrice, minSize, maxSize, ramType, timings);
        return ResponseEntity.ok(productsService.getTotalRamFiltered(filter));
    }

    @GetMapping("/category/cpu/amount")
    public ResponseEntity<Long> getTotalCpusFiltered (
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minCores,
            @RequestParam(required = false) Integer maxCores,
            @RequestParam(required = false) Integer minThreads,
            @RequestParam(required = false) Integer maxThreads,
            @RequestParam(required = false) Integer minTdp,
            @RequestParam(required = false) Integer maxTdp,
            @RequestParam(required = false) String socket
    ) {
        CpuFilter filter = new CpuFilter(minPrice, maxPrice, minCores, maxCores,
                minThreads, maxThreads, minTdp, maxTdp, socket);
        return ResponseEntity.ok(productsService.getTotalCpuFiltered(filter));
    }

    @GetMapping("/category/psu/amount")
    public ResponseEntity<Long> getTotalPsuFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minWatt,
            @RequestParam(required = false) Integer maxWatt,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String efficiencyRating
    ) {
        PsuFilter filter = new PsuFilter(minPrice, maxPrice, minWatt, maxWatt, size, efficiencyRating);
        return ResponseEntity.ok(productsService.getTotalPsuFiltered(filter));
    }

    @GetMapping("category/hdd/amount")
    public ResponseEntity<Long> getTotalHddFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize
    ) {
        HddFilter filter = new HddFilter(minPrice, maxPrice, minSize, maxSize);
        return ResponseEntity.ok(productsService.getTotalHddFiltered(filter));
    }

    @GetMapping("/category/ssd/amount")
    public ResponseEntity<Long> getTotalSsdFiltered(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false) String protocol,
            @RequestParam(required = false) String formFactor
    ) {
        SsdFilter filter = new SsdFilter(minPrice, maxPrice, minSize, maxSize, protocol, formFactor);
        return ResponseEntity.ok(productsService.getTotalSsdFiltered(filter));
    }

    @Operation(summary = "Modify a product")
    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
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

    @PostMapping("review")
    public ResponseEntity<ProductDto> addReview(
            @RequestBody ReviewRequest review,
            @RequestParam int productId,
            HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String name = jwtService.extractUsername(token);
        return ResponseEntity.ok(productsService.addReview(userService.getIdByUsername(name), productId,
                review));
    }

    @DeleteMapping("review")
    @PreAuthorize("hasRole('ADMIN') || #name == authentication.name")
    public ResponseEntity<String> deleteReview(
            @RequestParam int id,
            @RequestParam String name,
            HttpServletRequest request
    ) {
        productsService.deleteReview(userService.getIdByUsername(name), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
