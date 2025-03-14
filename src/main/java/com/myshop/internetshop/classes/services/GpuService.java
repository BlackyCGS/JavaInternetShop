package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.enums.ProductTableId;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.GpuRepository;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpuService {

    private final GpuRepository gpuRepository;

    @Autowired
    public GpuService(GpuRepository gpuRepository, ProductsRepository productsRepository) {
        this.gpuRepository = gpuRepository;
    }

    public List<GpuDto> getAllGpus(String producer, int boostClock, int displayPort,
                                   int hdmi, int tdp, int vram) {
        List<Gpu> gpus = gpuRepository.searchGpu(producer, boostClock,
                displayPort, hdmi, tdp, vram);
        return getGpuDtos(gpus);
    }

    private List<GpuDto> getGpuDtos(List<Gpu> gpus) {
        if (gpus.isEmpty()) {
            throw new NotFoundException("There are no gpus found");
        }
        List<GpuDto> gpuDtos = new ArrayList<>();
        for (Gpu gpu : gpus) {
            GpuDto gpuDto = convertToDto(gpu);
            gpuDtos.add(gpuDto);
        }
        return gpuDtos;
    }

    public GpuDto getGpuById(int id) {
        if (gpuRepository.existsById(id)) {
            return convertToDto(gpuRepository.findByProductId(id));
        } else {
            throw new NotFoundException("There is no gpu with id " + id);
        }
    }

    public List<GpuDto> getGpuByName(String name) {
        List<Gpu> gpus = gpuRepository.findByName(name);
        return getGpuDtos(gpus);
    }

    @Transactional
    public Gpu saveGpu(GpuRequest gpuRequest) {
        Gpu gpu;
        gpu = gpuRequest.toEntity();
        Product product = new Product();
        product.setName(gpu.getName());
        product.setPrice(gpu.getPrice());
        product.setCategoryId(ProductTableId.GPU.getTableId());
        gpu.setProduct(product);
        return gpuRepository.save(gpu);
    }

    public GpuRequest saveGpuRequest(GpuRequest gpuRequest) {
        Gpu gpu = gpuRequest.toEntity();
        gpuRepository.save(gpu);
        return gpuRequest;
    }

    @Transactional
    public void deleteGpu(int id) {
        gpuRepository.deleteByProductId(id);
    }

    public GpuDto convertToDto(Gpu gpu) {
        return new GpuDto(gpu.getProductId(), gpu.getName(), gpu.getProducer(),
                gpu.getBoostClock(), gpu.getDisplayPort(), gpu.getDvi(), gpu.getHdmi(),
                gpu.getTdp(), gpu.getVga(), gpu.getVram(), gpu.getPrice());
    }
}
