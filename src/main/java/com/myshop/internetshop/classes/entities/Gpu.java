package com.myshop.internetshop.classes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "gpu")
public class Gpu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1000")
    private int tableId = 1000;

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

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getBoostClock() {
        return boostClock;
    }

    public void setBoostClock(int boostClock) {
        this.boostClock = boostClock;
    }

    public int getVram() {
        return vram;
    }

    public void setVram(int vram) {
        this.vram = vram;
    }

    public int getTdp() {
        return tdp;
    }

    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    public int getHdmi() {
        return hdmi;
    }

    public void setHdmi(int hdmi) {
        this.hdmi = hdmi;
    }

    public int getDisplayPort() {
        return displayPort;
    }

    public void setDisplayPort(int displayPort) {
        this.displayPort = displayPort;
    }

    public int getDvi() {
        return dvi;
    }

    public void setDvi(int dvi) {
        this.dvi = dvi;
    }

    public int getVga() {
        return vga;
    }

    public void setVga(int vga) {
        this.vga = vga;
    }
}
