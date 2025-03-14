package com.myshop.internetshop.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.GpuRepository;
import com.myshop.internetshop.classes.repositories.ProductsRepository;
import com.myshop.internetshop.classes.services.GpuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GpuServiceTest {

    @Mock
    private GpuRepository gpuRepository;

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private GpuService gpuService;

    private Gpu gpu;
    private GpuRequest gpuRequest;

    @BeforeEach
    void setUp() {
        gpu = new Gpu();
        gpu.setProductId(1);
        gpu.setName("RTX 3090");
        gpu.setProducer("NVIDIA");
        gpu.setBoostClock(1800);
        gpu.setVram(24);
        gpu.setTdp(350);
        gpu.setHdmi(1);
        gpu.setDisplayPort(3);
        gpu.setDvi(0);
        gpu.setVga(0);
        gpu.setPrice(1499.99f);

        gpuRequest = new GpuRequest();
        gpuRequest.setBaseInfo("RTX 3090", "NVIDIA", "1800", "24", "1499.99");
        gpuRequest.setAdditionalInfo(3, 0, 1, "350", 0);
    }

    @Test
    void testGetGpuById_Found() {
        when(gpuRepository.existsById(1)).thenReturn(true);
        when(gpuRepository.findByProductId(1)).thenReturn(gpu);

        GpuDto result = gpuService.getGpuById(1);
        assertEquals("RTX 3090", result.getName());
    }

    @Test
    void testGetGpuById_NotFound() {
        when(gpuRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> gpuService.getGpuById(1));
    }

    @Test
    void testGetGpuByName_Found() {
        when(gpuRepository.findByName("RTX 3090")).thenReturn(List.of(gpu));
        List<GpuDto> result = gpuService.getGpuByName("RTX 3090");
        assertEquals(1, result.size());
        assertEquals("RTX 3090", result.get(0).getName());
    }

    @Test
    void testGetGpuByName_NotFound() {
        when(gpuRepository.findByName("RTX 3090")).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> gpuService.getGpuByName("RTX 3090"));
    }

    @Test
    void testSaveGpu() {
        when(gpuRepository.save(any(Gpu.class))).thenReturn(gpu);
        Gpu savedGpu = gpuService.saveGpu(gpuRequest);
        assertNotNull(savedGpu);
        assertEquals("RTX 3090", savedGpu.getName());
    }

    @Test
    void testDeleteGpu() {
        doNothing().when(gpuRepository).deleteByProductId(1);
        gpuService.deleteGpu(1);
        verify(gpuRepository, times(1)).deleteByProductId(1);
    }
}