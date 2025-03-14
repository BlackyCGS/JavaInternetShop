package com.myshop.internetshop.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GpuRequestTest {

    private GpuRequest gpuRequest;

    @BeforeEach
    void setUp() {
        gpuRequest = new GpuRequest();
    }

    @Test
    void parseIntegerShouldReturnParsedValue() {
        assertEquals(100, gpuRequest.parseInteger("100 MHz"));
        assertEquals(500, gpuRequest.parseInteger("500W"));
    }

    @Test
    void parseIntegerShouldReturnNullForInvalidValues() {
        assertNull(gpuRequest.parseInteger(null));
        assertNull(gpuRequest.parseInteger(""));
        assertNull(gpuRequest.parseInteger("NoNumbers"));
    }

    @Test
    void toEntityShouldConvertToGpuEntity() {
        gpuRequest = new GpuRequest();
        gpuRequest.setBaseInfo("RTX 4090", "NVIDIA", "2235 MHz", "24 GB", "250$");
        gpuRequest.setAdditionalInfo(3, 0, 1, "450W", 0);

        Gpu gpu = gpuRequest.toEntity();

        assertNotNull(gpu);
        assertEquals("RTX 4090", gpu.getName());
        assertEquals("NVIDIA", gpu.getProducer());
        assertEquals(2235, gpu.getBoostClock());
        assertEquals(24, gpu.getVram());
        assertEquals(450, gpu.getTdp());
        assertEquals(1, gpu.getHdmi());
        assertEquals(3, gpu.getDisplayPort());
        assertEquals(0, gpu.getDvi());
        assertEquals(0, gpu.getVga());
        assertEquals(250, gpu.getPrice());
    }
}