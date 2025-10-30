package com.myshop.internetshop.classes.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "pc_case")
public class PcCase {

    @Id
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String motherboard;

    @Column(nullable = false)
    private String powerSupply;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public PcCase() { /* Default constructor */
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

}
