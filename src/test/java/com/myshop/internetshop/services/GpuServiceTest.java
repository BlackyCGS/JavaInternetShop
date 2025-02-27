package com.myshop.internetshop.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.GpuDto;
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

    public static final String NAME = "RTX 3080";
    public static final String PRODUCER = "NVIDIA";
    @Mock
    private GpuRepository gpuRepository;

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private GpuService gpuService;

    private Gpu gpu;

    @BeforeEach
    void setUp() {
        gpu = new Gpu();
        gpu.setBaseInfo(1, NAME, PRODUCER, 1800, 10);
        gpu.setAdditionalInfo(3, 1, 1, 320, 0);

    }

    @Test
    void getAllGpusShouldReturnListOfGpuDtos() {
        when(gpuRepository.searchGpu(any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(gpu));

        List<GpuDto> result = gpuService.getAllGpus(PRODUCER, 1800, 3, 1, 320, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(NAME, result.get(0).getName());
    }

    @Test
    void getAllGpusShouldThrowNotFoundException() {
        when(gpuRepository.searchGpu(any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(NotFoundException.class, () ->
                gpuService.getAllGpus(PRODUCER, 1800, 3, 1, 320, 10));
    }

    @Test
    void getGpuByIdShouldReturnGpuDto() {
        when(gpuRepository.existsById(1)).thenReturn(true);
        when(gpuRepository.findByProductId(1)).thenReturn(gpu);

        GpuDto result = gpuService.getGpuById(1);

        assertNotNull(result);
        assertEquals(NAME, result.getName());
    }

    @Test
    void getGpuByIdShouldThrowNotFoundException() {
        when(gpuRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> gpuService.getGpuById(1));
    }

    @Test
    void getGpuByNameShouldReturnListOfGpuDtos() {
        when(gpuRepository.findByName(NAME)).thenReturn(Collections.singletonList(gpu));

        List<GpuDto> result = gpuService.getGpuByName(NAME);

        assertFalse(result.isEmpty());
        assertEquals(NAME, result.get(0).getName());
    }

    @Test
    void saveGpuShouldSaveAndReturnGpu() {
        when(gpuRepository.save(any(Gpu.class))).thenReturn(gpu);
        when(gpuRepository.count()).thenReturn(1L);

        Gpu savedGpu = gpuService.saveGpu(gpu);

        assertNotNull(savedGpu);
        assertEquals(NAME, savedGpu.getName());
    }

    @Test
    void deleteGpuShouldDeleteGpu() {
        doNothing().when(productsRepository).deleteById(anyLong());
        doNothing().when(gpuRepository).deleteById(anyInt());

        assertDoesNotThrow(() -> gpuService.deleteGpu(1));
    }
}
