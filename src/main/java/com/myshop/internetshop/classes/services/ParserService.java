package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ParserService {

    private final Random random = new Random();

    public List<ProductDto> massGpuParser(List<GpuRequest> gpuRequests) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (GpuRequest gpuRequest : gpuRequests) {
            Product product = new Product();
            Gpu gpu = gpuRequest.toEntity();
            product.setName(gpu.getName());
            product.setGpu(gpu);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massMotherboardParser(List<MotherboardRequest> motherboards) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (MotherboardRequest motherboardRequest : motherboards) {
            Product product = new Product();
            Motherboard motherboard = motherboardRequest.toEntity();
            product.setName(motherboard.getName());
            product.setMotherBoard(motherboard);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massCaseParser(List<CaseRequest> cases) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (CaseRequest caseRequest : cases) {
            Product product = new Product();
            PcCase pcCase = caseRequest.toEntity();
            product.setName(pcCase.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setPcCase(pcCase);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massRamParser(List<RamRequest> rams) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (RamRequest ramRequest : rams) {
            Product product = new Product();
            Ram ram = ramRequest.toEntity();
            product.setName(ram.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setRam(ram);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massCpuParser(List<CpuRequest> cpus) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (CpuRequest cpuRequest : cpus) {
            Product product = new Product();
            Cpu cpu = cpuRequest.toEntity();
            product.setName(cpu.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setCpu(cpu);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massPsuParser(List<PsuRequest> psuRequests) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (PsuRequest psuRequest : psuRequests) {
            Product product = new Product();
            Psu psu = psuRequest.toEntity();
            product.setName(psu.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setPsu(psu);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massHddParser(List<HddRequest> hdds) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (HddRequest hddRequest : hdds) {
            Product product = new Product();
            Hdd hdd = hddRequest.toEntity();
            product.setName(hdd.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setHdd(hdd);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public List<ProductDto> massSsdParser(List<SsdRequest> ssds) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (SsdRequest ssdRequest : ssds) {
            Product product = new Product();
            Ssd ssd = ssdRequest.toEntity();
            product.setName(ssd.getName());
            float price = random.nextFloat(2000);
            price = (float) (Math.ceil(price*100) / 100);
            product.setPrice(price);
            product.setSsd(ssd);
            productDtos.add(convertToDto(product));
        }
        return productDtos;
    }

    public ProductDto convertToDto(Product product) {
        return new ProductDto(product);
    }
}
