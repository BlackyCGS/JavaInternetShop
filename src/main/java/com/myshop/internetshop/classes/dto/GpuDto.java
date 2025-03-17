package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Gpu;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GpuDto {
    private int productId;
    private String name;
    private String producer;
    private int boostClock;
    private int displayPort;
    private int dvi;
    private int hdmi;
    private int tdp;
    private int vga;
    private int vram;
    private float price;

    public GpuDto() {
        /*Default Constructor*/
    }
    public GpuDto(Gpu gpu) {
        this.productId = gpu.getProductId();
        this.name = gpu.getName();
        this.producer = gpu.getProducer();
        this.boostClock = gpu.getBoostClock();
        this.displayPort = gpu.getDisplayPort();
        this.dvi = gpu.getDvi();
        this.hdmi = gpu.getHdmi();
        this.tdp = gpu.getTdp();
        this.vga = gpu.getVga();
        this.vram = gpu.getVram();
        this.price = gpu.getPrice();
    }
    public void setBaseInfo(int productId, String name, String producer) {
        this.productId = productId;
        this.name = name;
        this.producer = producer;
    }

    public void setAdditionalInfo(int boostClock, int displayPort,
        int tdp,int vram, float price) {
        this.boostClock = boostClock;
        this.displayPort = displayPort;
        this.tdp = tdp;
        this.vram = vram;
        this.price = price;
    }

    public void setPorts(int hdmi, int dvi, int vga) {
        this.hdmi = hdmi;
        this.dvi = dvi;
        this.vga = vga;
    }


}
