package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@SuppressWarnings("java:S7027")
public class Motherboard {

    @Id
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String socket;

    @Column(nullable = false)
    private String chipset;

    @Column(nullable = false)
    private String formFactor;

    @Column(nullable = false)
    private String memoryType;

    @Column(nullable = false)
    private int sata;

    @Column(nullable = false)
    private int ramSlots;

    @Column(nullable = false)
    private int vga;

    @Column(nullable = false)
    private int dvi;

    @Column(nullable = false)
    private int displayPort;

    @Column(nullable = false)
    private int hdmi;

    @SuppressWarnings("java:S7027")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;
}
