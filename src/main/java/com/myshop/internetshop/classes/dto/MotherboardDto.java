package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.Motherboard;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MotherboardDto {
    private Integer productId;
    @NotBlank(message = "name can not be blank")
    private String name;
    @NotBlank(message = "producer can not be blank")
    private String producer;
    @NotBlank(message = "socket can not be blank")
    private String socket;
    @NotBlank(message = "chipset can not be blank")
    private String chipset;
    @NotBlank(message = "form factor can not be blank")
    private String formFactor;
    @NotBlank(message = "memory type can not be blank")
    private String memoryType;
    @NotBlank(message = "sata can not be blank")
    @Min(value = 0)
    private int sata;
    @NotBlank(message = "ram slots can not be blank")
    @Min(value = 0)
    private int ramSlots;
    @NotBlank(message = "vga can not be blank")
    @Min(value = 0)
    private int vga;
    @NotBlank(message = "dvi can not be blank")
    @Min(value = 0)
    private int dvi;
    @NotBlank(message = "display port can not be blank")
    @Min(value = 0)
    private int displayPort;
    @NotBlank(message = "hdmi can not be blank")
    @Min(value = 0)
    private int hdmi;

    public MotherboardDto(Motherboard motherboard) {
        this.productId = motherboard.getProductId();
        this.name = motherboard.getName();
        this.producer = motherboard.getProducer();
        this.socket = motherboard.getSocket();
        this.chipset = motherboard.getChipset();
        this.formFactor = motherboard.getFormFactor();
        this.memoryType = motherboard.getMemoryType();
        this.sata = motherboard.getSata();
        this.ramSlots = motherboard.getRamSlots();
        this.vga = motherboard.getVga();
        this.dvi = motherboard.getDvi();
        this.displayPort = motherboard.getDisplayPort();
        this.hdmi = motherboard.getHdmi();
    }

    public Motherboard toEntity() {
        Motherboard motherboard = new Motherboard();
        motherboard.setProductId(this.productId);
        motherboard.setName(this.name);
        motherboard.setProducer(this.producer);
        motherboard.setSocket(this.socket);
        motherboard.setChipset(this.chipset);
        motherboard.setFormFactor(this.formFactor);
        motherboard.setMemoryType(this.memoryType);
        motherboard.setSata(this.sata);
        motherboard.setRamSlots(this.ramSlots);
        motherboard.setVga(this.vga);
        motherboard.setDvi(this.dvi);
        motherboard.setDisplayPort(this.displayPort);
        motherboard.setHdmi(this.hdmi);
        return motherboard;
    }
}
