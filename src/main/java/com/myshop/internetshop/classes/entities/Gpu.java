package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@SuppressWarnings("java:S7027")
public class Gpu {

    @Id
    private Integer productId;

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

    //@OneToOne(mappedBy = "gpu", cascade = CascadeType.ALL)
    @SuppressWarnings("java:S7027")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void setAdditionalInfo(int displayPort, int dvi, int hdmi, int tdp, int vga) {
        this.displayPort = displayPort;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.tdp = tdp;
        this.vga = vga;

    }

    public Product parseToProduct() {
        Product returnProduct = new Product();
        returnProduct.setName(this.name);
        return returnProduct;
    }
}
