package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Gpu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GpuDto {
    private int productId;
    @NotBlank(message = "name can not be blank")
    private String name;
    @NotBlank(message = "producer can not be blank")
    private String producer;
    @NotBlank(message = "boostClock can not be blank")
    @Min(value = 0)
    private int boostClock;
    @NotBlank(message = "displayPort can not be blank")
    @Min(value = 0)
    private int displayPort;
    @NotBlank(message = "dvi can not be blank")
    @Min(value = 0)
    private int dvi;
    @NotBlank(message = "hdmi can not be blank")
    @Min(value = 0)
    private int hdmi;
    @NotBlank(message = "tdp can not be blank")
    @Min(value = 0)
    private int tdp;
    @NotBlank(message = "vga can not be blank")
    @Min(value = 0)
    private int vga;
    @NotBlank(message = "vram can not be blank")
    @Min(value = 0)
    private int vram;
    @NotBlank(message = "price can not be blank")
    @Min(value = 0)
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
    }

    public void setBaseInfo(int productId, String name, String producer) {
        this.productId = productId;
        this.name = name;
        this.producer = producer;
    }

    public void setAdditionalInfo(int boostClock, int displayPort,
        int tdp, int vram, float price) {
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
