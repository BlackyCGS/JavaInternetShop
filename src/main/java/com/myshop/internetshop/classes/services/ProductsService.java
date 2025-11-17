package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.cache.Cache;
import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.entities.*;
import com.myshop.internetshop.classes.exceptions.ConflictException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.exceptions.ValidationException;
import com.myshop.internetshop.classes.exceptions.BadRequestException;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.repositories.ReviewRepository;
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
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final Cache<Product> productCache;
    private static final String PRODUCTS_CACHE_NAME = "products-";

    @Autowired
    public ProductsService(ProductsRepository productsRepository,
                           Cache<Product> productCache, UserService userService,
                           ReviewRepository reviewRepository) {
        this.productsRepository = productsRepository;
        this.productCache = productCache;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
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
        if (productDto.getPcCase() != null) {
            PcCase pcCase = product.getPcCase();
            pcCase.setProduct(product);
        }
        if (productDto.getRam() != null) {
            Ram ram = product.getRam();
            ram.setProduct(product);
        }
        if (productDto.getCpu() != null) {
            Cpu cpu = product.getCpu();
            cpu.setProduct(product);
        }
        if (productDto.getPsu() != null) {
            Psu psu = product.getPsu();
            psu.setProduct(product);
        }
        if (productDto.getHdd() != null) {
            Hdd hdd = product.getHdd();
            hdd.setProduct(product);
        }
        if (productDto.getSsd() != null) {
            Ssd ssd = product.getSsd();
            ssd.setProduct(product);
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
        List<PcCase> pcCases = products.stream().map(Product::getPcCase).toList();
        pcCases.forEach(pcCase -> {
            if (pcCase != null) {
                pcCase.setProduct(finalProducts.get(pcCases.indexOf(pcCase)));
            }
        });
        List<Ram> rams = products.stream().map(Product::getRam).toList();
        rams.forEach(ram -> {
            if (ram != null) {
                ram.setProduct(finalProducts.get(rams.indexOf(ram)));
            }
        });
        List<Cpu> cpus = products.stream().map(Product::getCpu).toList();
        cpus.forEach(cpu -> {
            if (cpu != null) {
                cpu.setProduct(finalProducts.get(cpus.indexOf(cpu)));
            }
        });
        List<Psu> psus = products.stream().map(Product::getPsu).toList();
        psus.forEach(psu -> {
            if (psu != null) {
                psu.setProduct(finalProducts.get(psus.indexOf(psu)));
            }
        });
        List<Hdd> hdds = products.stream().map(Product::getHdd).toList();
        hdds.forEach(hdd -> {
            if (hdd != null) {
                hdd.setProduct(finalProducts.get(hdds.indexOf(hdd)));
            }
        });
        List<Ssd> ssds = products.stream().map(Product::getSsd).toList();
        ssds.forEach(ssd -> {
            if (ssd != null) {
                ssd.setProduct(finalProducts.get(ssds.indexOf(ssd)));
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
        if (productDto.getPcCase() !=null) {
            categoryCount++;
        }
        if (productDto.getRam() != null) {
            categoryCount++;
        }
        if (productDto.getCpu() != null) {
            categoryCount++;
        }
        if (productDto.getPsu() != null) {
            categoryCount++;
        }
        if (productDto.getHdd() != null) {
            categoryCount++;
        }
        if (productDto.getSsd() != null) {
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

    @Transactional
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
        product.setStock(productDto.getStock());
        if (productDto.getGpu() != null) {
            Gpu gpu = productDto.getGpu().toEntity();
            gpu.setProductId(product.getId());
            product.setGpu(gpu);
            gpu.setProduct(product);
        }
        if (productDto.getMotherboard() != null) {
            Motherboard motherboard = productDto.getMotherboard().toEntity();
            motherboard.setProductId(product.getId());
            product.setMotherBoard(motherboard);
            motherboard.setProduct(product);
        }
        if (productDto.getPcCase() != null) {
            PcCase pcCase = productDto.getPcCase().toEntity();
            pcCase.setProductId(product.getId());
            product.setPcCase(pcCase);
            pcCase.setProduct(product);
        }
        if (productDto.getRam() != null) {
            Ram ram = productDto.getRam().toEntity();
            ram.setProductId(product.getId());
            product.setRam(ram);
            ram.setProduct(product);
        }
        if (productDto.getCpu() != null) {
            Cpu cpu = productDto.getCpu().toEntity();
            cpu.setProductId(product.getId());
            product.setCpu(cpu);
            cpu.setProduct(product);
        }
        if (productDto.getPsu() != null) {
            Psu psu = productDto.getPsu().toEntity();
            psu.setProductId(product.getId());
            product.setPsu(psu);
            psu.setProduct(product);
        }
        if (productDto.getHdd() != null) {
            Hdd hdd = productDto.getHdd().toEntity();
            hdd.setProductId(product.getId());
            product.setHdd(hdd);
            hdd.setProduct(product);
        }
        if (productDto.getSsd() != null) {
            Ssd ssd = productDto.getSsd().toEntity();
            ssd.setProductId(product.getId());
            product.setSsd(ssd);
            ssd.setProduct(product);
        }
        if (productsRepository.existsById((long) productDto.getId())) {
            Product tmp = productsRepository.findById(productDto.getId());
            product.setReviews(tmp.getReviews());
            product.setRating(tmp.getRating());
            product.setRatingAmount(tmp.getRatingAmount());
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

    public  List<ProductDto> gpuFilter(Pageable pageable, GpuFilter filter) {

        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("GPU"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice(),
                        filter.getMaxPrice()))
                .and(ProductSpecifications.gpuSpecs(filter));
        List<ProductDto> productDtos =
                productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public List<ProductDto> motherboardFilter(Pageable pageable, MotherboardFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("motherboard"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.motherboardSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalGpusFiltered(GpuFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("GPU"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice(),
                        filter.getMaxPrice()))
                .and(ProductSpecifications.gpuSpecs(filter));
        return productsRepository.count(specs);
    }

    public Long getTotalMotherboardsFiltered(MotherboardFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("motherboard"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.motherboardSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> pcCaseFilter(Pageable pageable,PcCaseFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("pcCase"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.pcCaseSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalPcCaseFiltered(PcCaseFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("pcCase"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.pcCaseSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> ramFilter(Pageable pageable, RamFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("ram"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.ramSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalRamFiltered(RamFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("ram"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.ramSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> cpuFilter(Pageable pageable, CpuFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("cpu"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.cpuSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalCpuFiltered(CpuFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("cpu"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.cpuSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> psuFilter(Pageable pageable, PsuFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("psu"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.psuSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalPsuFiltered(PsuFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("psu"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.psuSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> hddFilter(Pageable pageable, HddFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("hdd"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.hddSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalHddFiltered(HddFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("hdd"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.hddSpecs(filter));
        return productsRepository.count(specs);
    }

    public List<ProductDto> ssdFilter(Pageable pageable, SsdFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("ssd"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.ssdSpecs(filter));
        List<ProductDto> productDtos = productsRepository.findAll(specs, pageable).stream().map(this::convertToDto).toList();
        if(productDtos.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        return productDtos;
    }

    public long getTotalSsdFiltered(SsdFilter filter) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.categoryEquals("ssd"))
                .and(ProductSpecifications.priceBetween(filter.getMinPrice()
                        , filter.getMaxPrice()))
                .and(ProductSpecifications.ssdSpecs(filter));
        return productsRepository.count(specs);
    }

    public float applyRating(int productId, int newRating) {
        Product product = productsRepository.findById(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }
        int amount = product.getRatingAmount();
        float rating = product.getRating();
        float ratingTmp = rating*amount;
        ratingTmp += newRating;
        amount++;
        rating = ratingTmp / amount;
        product.setRatingAmount(amount);
        product.setRating(rating);
        productsRepository.save(product);
        return rating;
    }

    public float removeRating(int productId, int newRating) {
        Product product = productsRepository.findById(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }
        int amount = product.getRatingAmount();
        float rating = product.getRating();
        float ratingTmp = rating*amount;
        ratingTmp -= newRating;
        amount--;
        if(amount != 0) {
            rating = ratingTmp / amount;
        }
        else {
            rating = 0;
        }
        product.setRatingAmount(amount);
        product.setRating(rating);
        productsRepository.save(product);
        return rating;
    }

    public ProductDto addReview(int userId, int productId, ReviewRequest reviewRequest) {
        Product product = productsRepository.findById(productId);
        if(reviewRepository.existsByProductAndUser(product,
                userService.findByUserId(userId))) {
            throw new ConflictException("Review on this product already exists");
        }
        if(product == null) {
            throw new NotFoundException("Product not found");
        }
        Review review = new Review();
        review.setRating(reviewRequest.getRating());
        review.setTitle(reviewRequest.getTitle());
        review.setText(reviewRequest.getText());
        review.setProduct(product);
        review.setUser(userService.findByUserId(userId));
        List<Review> reviews = product.getReviews();
        reviews.add(review);
        product.setReviews(reviews);
        this.applyRating(productId, reviewRequest.getRating());
        product = productsRepository.save(product);
        return convertToDto(product);
    }

    public void deleteReview(int userId, int productId) {
        Product product = productsRepository.findById(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }
        Review review = reviewRepository.findByProductAndUser(product, userService.findByUserId(userId));

        this.removeRating(productId, review.getRating());

        reviewRepository.delete(review);
    }

    public ProductDto increaseStock(int productId, int stock) {
        if (stock <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        Product product = productsRepository.findById(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }

        product.setStock(product.getStock() + stock);
        return new ProductDto(productsRepository.save(product));
    }

    public Integer decreaseStock(int productId, int stock) {
        Product product = productsRepository.findById(productId);
        if(product == null) {
            throw new NotFoundException("Product not found");
        }

        if(product.getStock() < stock) {
            throw new BadRequestException("There is not that many products");
        }
        product.setStock(product.getStock() - stock);
        return productsRepository.save(product).getStock();
    }
}
