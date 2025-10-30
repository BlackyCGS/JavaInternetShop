package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Entity
@Table(name = "products")
@SuppressWarnings("java:S7027")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private float price;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Gpu gpu;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Motherboard motherBoard;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private PcCase pcCase;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Ram ram;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Cpu cpu;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Hdd hdd;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Psu psu;

    @Getter
    @Setter
    @SuppressWarnings("java:S7027")
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Ssd ssd;

    @OneToMany(
            mappedBy = "product",
            cascade =
                    {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType
                            .REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<OrderProduct> orders;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {}
}
