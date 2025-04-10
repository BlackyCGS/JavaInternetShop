package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.exceptions.ValidationException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
public class ProductsService {
    private final Logger logger = LoggerFactory.getLogger(ProductsService.class);
    private final ProductsRepository productsRepository;
    private final Cache<Product> productCache;
    private static final String PRODUCTS_CACHE_NAME = "products-";

    @Autowired
    public ProductsService(ProductsRepository productsRepository,
                           Cache<Product> productCache,
                           @Lazy GpuService gpuService) {
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
        logger.info("getAllProducts return");
        return productDtos;
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }

    public Product getProductById(long productId) {
        String cacheKey = PRODUCTS_CACHE_NAME + productId;
        if (productCache.contains(cacheKey)) {
            logger.info("getProductById return from cache");
            return productCache.get(cacheKey);
        }
        if (productsRepository.existsById(productId)) {
            Product product = productsRepository.findById(productId);
            productCache.put(cacheKey, product);
            logger.info("getProductById return");
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
        logger.info("getGpuByParams return");
        return productDtos;
    }

    public void deleteProductById(long productId) {

        String cacheKey = PRODUCTS_CACHE_NAME + productId;
        if (productsRepository.existsById(productId)) {
            if (productCache.contains(cacheKey)) {
                productCache.remove(cacheKey);
            }
            productsRepository.deleteById(productId);
            logger.info("Product with id {} deleted", productId);
        } else {
            throw new NotFoundException("There are already no product");
        }
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        validateProductDto(productDto);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        if (productDto.getGpu() != null) {
            product.setGpu(productDto.getGpu());
            Gpu gpu = productDto.getGpu();
            gpu.setProduct(product);
        }
        if (productDto.getMotherboard() != null) {
            product.setMotherBoard(productDto.getMotherboard());
            Motherboard motherboard = productDto.getMotherboard();
            motherboard.setProduct(product);
        }
        productsRepository.save(product);
        logger.info("saveProduct return");
        return new ProductDto(product);
    }

    public List<ProductDto> saveProducts(List<ProductDto> productDtos) {
        for (ProductDto productDto : productDtos) {
            validateProductDto(productDto);
        }
        List<Product> products = productDtos.stream()
                .map(ProductDto::toEntity).toList();
        List<Product> finalProducts = products;
        List<Gpu> gpus = productDtos.stream().map(ProductDto::getGpu).toList();
        gpus.forEach(gpu -> {
            if (gpu != null) {
                gpu.setProduct(finalProducts.get(gpus.indexOf(gpu)));
            }
            }
        );
        products = productsRepository.saveAll(products);
        logger.info("saveProducts return");
        return products.stream().map(this::convertToDto).toList();

    }

    public boolean existsById(long productId) {
        logger.info("existsById return");
        return productsRepository.existsById(productId);
    }

    @Transactional
    public void clearCache() {
        logger.info("clearCache call");
        productCache.clear();
    }

    private void validateProductDto(ProductDto productDto) {
        int categoryCount = 0;
        if (productDto.getGpu() != null) {
            categoryCount++;
        }
        if (productDto.getMotherboard() != null) {
            categoryCount++;
        }
        if (categoryCount != 1) {
            throw new ValidationException("ProductDto with name: "
                    + productDto.getName() + "is not valid because it has " + categoryCount + " categories");
        }
    }

    public void deleteAll() {
        productsRepository.deleteAll();
    }
}
