package com.myshop.internetshop.classes.dto;

public record GpuDto(int productId, String name, String producer, int boostClock,
                     int displayPort, int dvi, int hdmi, int tdp, int vga, int vram) {
    public String getName() {
        return name;
    }
}
