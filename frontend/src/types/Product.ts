// src/types/Product.ts
export interface Product {
    id: number;
    name: string;
    price: number;
    gpu?: Gpu;        // Только одно из двух
    motherboard?: Motherboard;
}

export interface CreateProduct {
    name: string;
    price: number;
    gpu?: Gpu;        // Только одно из двух
    motherboard?: Motherboard;
}

export interface Gpu {
    productId: number;
    name: string;
    producer: string;
    boostClock: number;
    vram: number;
    tdp: number;
    hdmi: number;
    displayPort: number;
    dvi: number;
    vga: number;
}

export interface Motherboard {
    productId: number;
    name: string;
    producer: string;
    socket: string;
    chipset: string;
    formFactor: string;
    memoryType: string;
    sata: number;
    ramSlots: number;
    vga: number;
    dvi: number;
    displayPort: number;
    hdmi: number;
}