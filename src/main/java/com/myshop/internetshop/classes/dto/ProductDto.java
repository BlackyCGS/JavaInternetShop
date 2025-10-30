package com.myshop.internetshop.classes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myshop.internetshop.classes.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Integer id;
    @NotBlank(message = "Product name can not be blank")
    private String name;
    @Min(value = 0, message = "Price must be more than 0")
    private float price;

    private GpuDto gpu;

    private MotherboardDto motherboard;

    private PcCaseDto pcCase;

    private RamDto ram;

    private CpuDto cpu;

    private PsuDto psu;

    private HddDto hdd;

    private SsdDto ssd;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        if(product.getGpu() != null) {
            this.gpu = new GpuDto(product.getGpu());
        } else {
            this.gpu = null;
        }
        if(product.getMotherBoard() != null) {
            this.motherboard = new MotherboardDto(product.getMotherBoard());
        } else {
            this.motherboard = null;
        }
        if(product.getPcCase() != null) {
            this.pcCase = new PcCaseDto(product.getPcCase());
        } else {
            this.pcCase = null;
        }
        if(product.getRam() != null) {
            this.ram = new RamDto(product.getRam());
        } else {
            this.ram = null;
        }
        if(product.getCpu() != null) {
            this.cpu = new CpuDto(product.getCpu());
        } else {
            this.cpu = null;
        }
        if(product.getPsu() != null) {
            this.psu = new PsuDto(product.getPsu());
        } else {
            this.psu = null;
        }
        if(product.getHdd() != null) {
            this.hdd = new HddDto(product.getHdd());
        } else {
            this.hdd = null;
        }
        if(product.getSsd() != null) {
            this.ssd = new SsdDto(product.getSsd());
        } else {
            this.ssd = null;
        }

        if(price == 0) {
            Random random = new Random();
            price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);

        }
    }

    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        if(this.getGpu() != null) {
            product.setGpu(this.gpu.toEntity());
            this.gpu.setProductId(null);
        }
        if(this.getMotherboard() != null) {
            product.setMotherBoard(this.motherboard.toEntity());
            this.motherboard.setProductId(null);
        }
        if(this.getPcCase() != null) {
            product.setPcCase(this.pcCase.toEntity());
            this.pcCase.setProductId(null);
        }
        if(this.getRam() != null) {
            product.setRam(this.ram.toEntity());
            this.ram.setProductId(null);
        }
        if(this.getCpu() != null) {
            product.setCpu(this.cpu.toEntity());
            this.cpu.setProductId(null);
        }
        if (this.getPsu() != null) {
            product.setPsu(this.psu.toEntity());
            this.psu.setProductId(null);
        }
        if(this.getHdd() != null) {
            product.setHdd(this.hdd.toEntity());
            this.hdd.setProductId(null);
        }
        if(this.getSsd() != null) {
            product.setSsd(this.ssd.toEntity());
            this.ssd.setProductId(null);
        }
        return product;

    }

}
