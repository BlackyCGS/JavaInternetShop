package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Products;
import com.myshop.internetshop.classes.repositories.GpuRepository;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GpuService {

    private final GpuRepository gpuRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public GpuService(GpuRepository gpuRepository, ProductsRepository productsRepository) {
        this.gpuRepository = gpuRepository;
        this.productsRepository = productsRepository;
    }

    public List<GpuDto> getAllGpus(String producer, int boostClock, int displayPort,
                                   int hdmi, int tdp, int vram) {
        List<Gpu> gpus = gpuRepository.searchGpu(producer, boostClock,
                displayPort, hdmi, tdp, vram);
        List<GpuDto> gpuDtos = new ArrayList<>();
        for (Gpu gpu : gpus) {
            GpuDto gpuDto = convertToDto(gpu);
            gpuDtos.add(gpuDto);
        }
        return gpuDtos;
    }

    public GpuDto getGpuById(int id) {
        return convertToDto(gpuRepository.findByProductId(id));
    }

    public List<GpuDto> getGpuByName(String name) {
        List<Gpu> gpus = gpuRepository.findByName(name);
        List<GpuDto> gpuDtos = new ArrayList<>();
        for (Gpu gpu : gpus) {
            GpuDto gpuDto = convertToDto(gpu);
            gpuDtos.add(gpuDto);
        }
        return gpuDtos;
    }

    @Transactional
    public Gpu saveGpu(Gpu gpu) {
        Random rand = new SecureRandom();
        int price = rand.nextInt(2000);
        gpuRepository.save(gpu);
        Products products = new Products(1000, (int) gpuRepository.count(),
                gpu.getName(), price);
        productsRepository.save(products);
        return gpu;
    }

    public void deleteGpu(int id) {
        productsRepository.deleteById((long) (10000000 + id));
        gpuRepository.deleteById(id);
    }

    public GpuDto convertToDto(Gpu gpu) {
        return new GpuDto(gpu.getProductId(), gpu.getName(), gpu.getProducer(),
                gpu.getBoostClock(), gpu.getDisplayPort(), gpu.getDvi(), gpu.getHdmi(),
                gpu.getTdp(), gpu.getVga(), gpu.getVram());
    }
}
