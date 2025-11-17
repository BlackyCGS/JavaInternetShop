// src/types/Product.ts
export interface Product {
    id: number;
    name: string;
    price: number;
    stock: number;
    rating: number;
    ratingAmount: number;
    reviews?: Review[];
    gpu?: Gpu;        // Только одно из двух
    motherboard?: Motherboard;
    cpu?: Cpu;
    hdd?: Hdd;
    pcCase?: PcCase;
    psu?: Psu;
    ram?: Ram;
    ssd?: Ssd;
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

export interface Cpu {
    productId: number;
    name: string;
    producer: string;
    socket: string;
    cores: number;
    threads: number;
    tdp: number;
}

export interface Hdd {
    productId: number;
    name: string;
    producer: string;
    size: number;
}

export interface PcCase {
    productId: number;
    name: string;
    producer: string;
    motherboard: string;
    powerSupply: string;
}

export interface Psu {
    productId: number;
    name: string;
    producer: string;
    watt: number;
    size: string;
    efficiencyRating: string;
}

export interface Ram {
    productId: number;
    name: string;
    producer: string;
    ramType: string;
    size: number;
    timings: string;
}

export interface Ssd {
    productId: number;
    name: string;
    producer: string;
    formFactor: string;
    protocol: string;
    size: number;
}

export interface Review {
    id: number
    title: string;
    text: string;
    rating: number;
    username: string;
}

export interface ReviewRequest {
    title: string;
    text: string;
    rating: number;
}