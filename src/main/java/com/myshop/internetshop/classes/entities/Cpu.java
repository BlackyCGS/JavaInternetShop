package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "cpu")
public class Cpu {
    @Id
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private int cores;

    @Column(nullable = false)
    private int threads;

    @Column(nullable = false)
    private int tdp;

    @Column(nullable = false)
    private String socket;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public Cpu() {/*default constructor*/}
}
