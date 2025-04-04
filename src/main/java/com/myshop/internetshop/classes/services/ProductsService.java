package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    private final GpuService gpuService;
    private final ProductsRepository productsRepository;
    private final Cache<Product> productCache;
    private static final String PRODUCTS_CACHE_NAME = "products-";
    @Autowired
    public ProductsService(ProductsRepository productsRepository,
                           Cache<Product> productCache,
                           @Lazy GpuService gpuService) {
        this.gpuService = gpuService;
        this.productsRepository = productsRepository;
        this.productCache = productCache;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productsRepository.findAll();
        if (products.isEmpty()) {
            throw new NotFoundException("There are no products");
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = convertToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }

    public Product getProductById(long productId) {
        String cacheKey = PRODUCTS_CACHE_NAME + productId;
        if (productCache.contains(cacheKey)) {
            return productCache.get(cacheKey);
        }
        if(productsRepository.existsById(productId)) {
            Product product = productsRepository.findById(productId);
            productCache.put(cacheKey, product);
            return product;
        }
        throw new NotFoundException("There is no product with id " + productId);
    }

    public List<ProductDto> getGpuByParams(String producer, int boostClock, int displayPort,
                                           int hdmi, int tdp, int vram) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productsRepository.findGpuByParams(
                producer, boostClock,
                displayPort, hdmi, tdp, vram);
        if (products.isEmpty()) {
            throw new NotFoundException("There is no gpu with these parameters");
        }
        for (Product product : products) {
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public void deleteProductById(long productId) {
        String cacheKey = PRODUCTS_CACHE_NAME + productId;
        if (productsRepository.existsById(productId)) {
            if (productCache.contains(cacheKey)) {
                productCache.remove(cacheKey);
            }
            productsRepository.deleteById(productId);
        } else {
            throw new NotFoundException("There are already no product");
        }
    }

    @Transactional
    public ProductDto saveGpu(GpuRequest gpuRequest) {
        Gpu gpu;
        gpu = gpuRequest.toEntity();
        Product product = new Product();
        product.setName(gpu.getName());
        product.setPrice(gpu.getPrice());
        gpu.setProduct(product);
        gpu = gpuService.save(gpu);
        productsRepository.save(product);
        product.setGpu(gpu);
        return new ProductDto(product);
    }

    public boolean existsById(long productId) {
        return productsRepository.existsById(productId);
    }

    public void clearCache() {
        productCache.clear();
    }
}
