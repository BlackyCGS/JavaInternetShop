package com.myshop.internetshop.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.controllers.GpuController;
import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.services.GpuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GpuControllerTest {

    @Mock
    private GpuService gpuService;

    @InjectMocks
    private GpuController gpuController;

    private Gpu gpu;
    private GpuDto gpuDto;
    private GpuRequest gpuRequest;

    @BeforeEach
    void setUp() {
        gpu = new Gpu();
        gpu.setName("RTX 4090");
        gpu.setProducer("NVIDIA");
        gpuDto = new GpuDto();
        gpuDto.setBaseInfo(1, "RTX 4090", "NVIDIA");
        gpuDto.setAdditionalInfo(2235, 3, 450, 24, 250);
        gpuDto.setPorts(1,0,0);
        gpuRequest = new GpuRequest();
        gpuRequest.setBaseInfo("RTX 4090", "NVIDIA", "2235 MHz", "24 GB", "250$");
        gpuRequest.setAdditionalInfo(3, 0, 1, "450W", 0);
    }

    @Test
    void getAllGpusShouldReturnListOfGpuDtos() {
        when(gpuService.getAllGpus(any(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(gpuDto));

        List<GpuDto> result = gpuController.getAllGpus(null, 0, 0, 0, 0, 0);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("RTX 4090", result.get(0).getName());
    }

    @Test
    void getGpuByIdShouldReturnGpuDto() {
        when(gpuService.getGpuById(1)).thenReturn(gpuDto);

        GpuDto result = gpuController.getGpuById(1);

        assertNotNull(result);
        assertEquals("RTX 4090", result.getName());
    }

    @Test
    void getGpuByNameShouldReturnListOfGpuDtos() {
        when(gpuService.getGpuByName("RTX 4090")).thenReturn(Collections.singletonList(gpuDto));

        List<GpuDto> result = gpuController.getGpuByName("RTX 4090");

        assertFalse(result.isEmpty());
        assertEquals("RTX 4090", result.get(0).getName());
    }


    @Test
    void deleteGpuShouldCallServiceMethod() {
        doNothing().when(gpuService).deleteGpu(1);

        gpuController.deleteGpu(1);

        verify(gpuService, times(1)).deleteGpu(1);
    }
}

