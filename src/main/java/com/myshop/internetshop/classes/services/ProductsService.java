package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.GpuFilter;
import com.myshop.internetshop.classes.dto.MotherboardFilter;
import com.myshop.internetshop.classes.dto.ProductDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.exceptions.ValidationException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.specifications.ProductSpecifications;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ProductsService {
    private final Logger logger = LoggerFactory.getLogger(ProductsService.class);
    private final ProductsRepository productsRepository;
    private final Cache<Product> productCache;
    private static final String PRODUCTS_CACHE_NAME = "products-";

    @Autowired
    public ProductsService(ProductsRepository productsRepository,
                           Cache<Product> productCache) {
        this.productsRepository = productsRepository;
        this.productCache = productCache;
    }

    public List<ProductDto> getAllProducts(Pageable pageable) {
        List<Product> products = productsRepository.findAll(pageable).toList();
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
            logger.info("getProductById return from cache");
            return productCache.get(cacheKey);
        }
        if (productsRepository.existsById(productId)) {
            Product product = productsRepository.findById(productId);
            productCache.put(cacheKey, product);
            return product;
        }
        throw new NotFoundException("There is no product with id " + productId);
    }

    public List<ProductDto> getGpuByParams(String producer, int boostClock, int displayPort,
                                           int hdmi, int tdp, int vram, Pageable pageable) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productsRepository.findGpuByParams(
                producer, boostClock,
                displayPort, hdmi, tdp, vram, pageable);
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
            logger.info("Product with id {} deleted", productId);
        } else {
            throw new NotFoundException("There are already no product");
        }
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        validateProductDto(productDto);
        Product product = productDto.toEntity();
        if (productDto.getGpu() != null) {
            Gpu gpu = product.getGpu();
            gpu.setProduct(product);
        }
        if (productDto.getMotherboard() != null) {
            Motherboard motherboard = product.getMotherBoard();
            motherboard.setProduct(product);
        }
        productsRepository.save(product);
        return new ProductDto(product);
    }

    public List<ProductDto> saveProducts(List<ProductDto> productDtos) {
        for (ProductDto productDto : productDtos) {
            validateProductDto(productDto);
        }
        List<Product> products = productDtos.stream()
                .map(ProductDto::toEntity).toList();
        List<Product> finalProducts = products;
        List<Gpu> gpus = products.stream().map(Product::getGpu).toList();
        gpus.forEach(gpu -> {
            if (gpu != null) {
                gpu.setProduct(finalProducts.get(gpus.indexOf(gpu)));
            }
            }
        );
        List<Motherboard> motherboards =
                products.stream().map(Product::getMotherBoard).toList();
        motherboards.forEach(motherboard -> {
            if (motherboard != null) {
                motherboard.setProduct(finalProducts.get(motherboards.indexOf(motherboard)));
            }
        });
        products = productsRepository.saveAll(products);
        return products.stream().map(this::convertToDto).toList();

    }

    public boolean existsById(long productId) {
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
                    + productDto.getName()
                    + "is not valid because it has " + categoryCount + " categories");
        }
    }

    public List<ProductDto> getMotherboards(Pageable pageable) {
        List<Product> products = productsRepository.getMotherboards(pageable);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public Integer getProductsCount(String category, String name) {

        return switch (category) {
            case "All" -> Math.toIntExact(productsRepository
                    .countAllByNameContainingIgnoreCase(name));
            case "Motherboard" -> productsRepository
                    .countByMotherBoardIsNotNullAndNameContainingIgnoreCase(name);
            case "Gpu" -> productsRepository
                    .countByGpuIsNotNullAndNameContainingIgnoreCase(name);
            default -> throw new ValidationException("Incorrect param");
        };
    }

    public void deleteAll() {
        productsRepository.deleteAll();
    }

    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        validateProductDto(productDto);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setId(productDto.getId());
        if (productDto.getGpu() != null) {
            Gpu gpu = productDto.getGpu().toEntity();
            gpu.setProductId(product.getId());
            product.setGpu(productDto.getGpu().toEntity());
            gpu.setProduct(product);
        }
        if (productDto.getMotherboard() != null) {
            Motherboard motherboard = productDto.getMotherboard().toEntity();
            motherboard.setProductId(product.getId());
            product.setMotherBoard(productDto.getMotherboard().toEntity());
            motherboard.setProduct(product);
        }
        if (productsRepository.existsById((long) productDto.getId())) {
        productsRepository.save(product);
        return new ProductDto(product);
        } else {
            throw new NotFoundException("Product with id " + product.getId() + " not found");
        }
    }

    public List<ProductDto> searchByName(String name, Pageable pageable) {
        List<ProductDto> productDtos = productsRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products with name " + name + " not found");
        }
        return productDtos;
    }

    public  List<ProductDto> gpuFilter(Pageable pageable, GpuFilter gpuFilter) {

        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("GPU"))
                .and(ProductSpecifications.priceBetween(gpuFilter.getMinPrice(),
                        gpuFilter.getMaxPrice()))
                .and(ProductSpecifications.gpuSpecs(gpuFilter.getMinVram(),
                        gpuFilter.getMaxVram(), gpuFilter.getMinTdp(),
                        gpuFilter.getMaxTdp(), gpuFilter.getMinBoostClock(), gpuFilter.getMaxBoostClock()));
        List<ProductDto> productDtos =
                productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public List<ProductDto> motherboardFilter(Pageable pageable, MotherboardFilter motherboardFilter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("motherboard"))
                .and(ProductSpecifications.priceBetween(motherboardFilter.getMinPrice()
                        , motherboardFilter.getMaxPrice()))
                .and(ProductSpecifications.motherboardSpecs(motherboardFilter.getSocket()
                        , motherboardFilter.getChipset()
                        , motherboardFilter.getFormFactor()
                        , motherboardFilter.getMemoryType()));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalGpusFiltered(GpuFilter gpuFilter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("GPU"))
                .and(ProductSpecifications.priceBetween(gpuFilter.getMinPrice(),
                        gpuFilter.getMaxPrice()))
                .and(ProductSpecifications.gpuSpecs(gpuFilter.getMinVram(),
                        gpuFilter.getMaxVram(), gpuFilter.getMinTdp(),
                        gpuFilter.getMaxTdp(), gpuFilter.getMinBoostClock(), gpuFilter.getMaxBoostClock()));
        return productsRepository.count(specs);
    }

    public Long getTotalMotherboardsFiltered(MotherboardFilter motherboardFilter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("motherboard"))
                .and(ProductSpecifications.priceBetween(motherboardFilter.getMinPrice()
                        , motherboardFilter.getMaxPrice()))
                .and(ProductSpecifications.motherboardSpecs(motherboardFilter.getSocket()
                        , motherboardFilter.getChipset()
                        , motherboardFilter.getFormFactor()
                        , motherboardFilter.getMemoryType()));
        return productsRepository.count(specs);
    }
}
