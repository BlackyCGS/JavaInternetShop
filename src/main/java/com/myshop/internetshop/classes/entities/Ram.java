package com.myshop.internetshop.classes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "ram")
public class Ram {

    @Id
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String ramType;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private String timings;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public Ram() {/*default constructor*/}

}
