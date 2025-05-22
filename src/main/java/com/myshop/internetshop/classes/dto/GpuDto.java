package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Gpu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GpuDto {
    private Integer productId;
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

    public Gpu toEntity() {
        Gpu gpu = new Gpu();
        gpu.setProductId(null);
        gpu.setName(this.name);
        gpu.setProducer(this.producer);
        gpu.setBoostClock(this.boostClock);
        gpu.setDisplayPort(this.displayPort);
        gpu.setDvi(this.dvi);
        gpu.setHdmi(this.hdmi);
        gpu.setTdp(this.tdp);
        gpu.setVga(this.vga);
        gpu.setVram(this.vram);
        return gpu;
    }

}
