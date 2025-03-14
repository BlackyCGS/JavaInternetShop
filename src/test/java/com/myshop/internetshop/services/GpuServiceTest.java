package com.myshop.internetshop.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.myshop.internetshop.classes.dto.GpuDto;
import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.GpuRepository;
import com.myshop.internetshop.classes.services.GpuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GpuServiceTest {

    @Mock
    public static final String NAME = "RTX 3080";
    public static final String PRODUCER = "NVIDIA";
    private GpuRepository gpuRepository;

    @InjectMocks
    private GpuService gpuService;

    private Gpu gpu;
    private GpuDto gpuDto;

    @BeforeEach
    void setUp() {
        gpu = new Gpu();
        gpu.setProductId(1);
        gpu.setName(NAME);
        gpu.setProducer(PRODUCER);
        gpu.setBoostClock(2500);
        gpu.setDisplayPort(3);
        gpu.setHdmi(1);
        gpu.setTdp(450);
        gpu.setVram(24);
        gpu.setPrice(1999);

        gpuDto = new GpuDto(1, NAME, PRODUCER, 2500, 3, 0, 1, 450, 0, 24, 1999);
    }
        @Test
        void getGpuById_WhenGpuExists_ShouldReturnGpuDto() {
            when(gpuRepository.existsById(1)).thenReturn(true);
            when(gpuRepository.findByProductId(1)).thenReturn(gpu);

            GpuDto result = gpuService.getGpuById(1);

            assertNotNull(result);
            assertEquals(gpuDto, result);
        }

        @Test
        void getGpuById_WhenGpuDoesNotExist_ShouldThrowNotFoundException() {
            when(gpuRepository.existsById(2)).thenReturn(false);

            Exception exception = assertThrows(NotFoundException.class, () -> gpuService.getGpuById(2));
            assertEquals("There is no gpu with id 2", exception.getMessage());
        }

        @Test
        void getGpuByName_ShouldReturnListOfGpuDtos() {
            when(gpuRepository.findByName("RTX 4090")).thenReturn(Arrays.asList(gpu));

            List<GpuDto> result = gpuService.getGpuByName("RTX 4090");

            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(gpuDto, result.get(0));
        }

        @Test
        void saveGpu_ShouldSaveGpuAndReturnEntity() {
            GpuRequest gpuRequest = new GpuRequest();
            gpuRequest.setBaseInfo( NAME, PRODUCER, "10", "500", "1000");
            gpuRequest.setAdditionalInfo(3, 1, 1, "320", 0);
            when(gpuRepository.save(any(Gpu.class))).thenReturn(gpu);

            Gpu result = gpuService.saveGpu(gpuRequest);

            assertNotNull(result);
            assertEquals("RTX 4090", result.getName());
        }

        @Test
        @Transactional
        void deleteGpu_ShouldDeleteGpu() {
            doNothing().when(gpuRepository).deleteByProductId(1);

            assertDoesNotThrow(() -> gpuService.deleteGpu(1));
        }

        @Test
        @Transactional
        void deleteGpu_WhenGpuDoesNotExist_ShouldThrowException() {
            doThrow(new EmptyResultDataAccessException(1)).when(gpuRepository).deleteByProductId(2);

            assertThrows(EmptyResultDataAccessException.class, () -> gpuService.deleteGpu(2));
        }
    }
