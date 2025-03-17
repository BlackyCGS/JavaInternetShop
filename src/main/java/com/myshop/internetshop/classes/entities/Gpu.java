package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myshop.internetshop.classes.enums.ProductTableId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "gpu")
public class Gpu {

    @Id
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1000")
    private int boostClock = 1000;

    @Column(nullable = false)
    private int vram = 1000;

    @Column(nullable = false)
    private int tdp = 100;

    @Column(nullable = false)
    private int hdmi = 0;

    @Column(nullable = false)
    private int displayPort = 0;

    @Column(nullable = false)
    private int dvi = 0;

    @Column(nullable = false)
    private int vga = 0;

    @Column(nullable = false)
    private float price = 0;

    //@OneToOne(mappedBy = "gpu", cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public Gpu() { /* Default constructor */
    }

    public void setBaseInfo(int productId, String name, String producer, int boostClock,
                        int vram) {
        this.productId = productId;
        this.name = name;
        this.producer = producer;
        this.boostClock = boostClock;
        this.vram = vram;
    }

    public void setAdditionalInfo(int displayPort, int dvi, int hdmi, int tdp, int vga,
            float price) {
        this.displayPort = displayPort;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.tdp = tdp;
        this.vga = vga;
        this.price = price;

    }

    public Product parseToProduct() {
        Product returnProduct = new Product();
        returnProduct.setName(this.name);
        returnProduct.setCategoryId(ProductTableId.GPU.getTableId());
        returnProduct.setPrice(this.price);
        return returnProduct;
    }
}
