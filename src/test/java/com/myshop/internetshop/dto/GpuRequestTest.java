package com.myshop.internetshop.dto;

import com.myshop.internetshop.classes.dto.GpuRequest;
import com.myshop.internetshop.classes.entities.Gpu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GpuRequestTest {

    private GpuRequest gpuRequest;

    @BeforeEach
    void setUp() {
        gpuRequest = new GpuRequest();
    }

    @Test
    void testSetBaseInfo() {
        gpuRequest.setBaseInfo("RTX 3080", "NVIDIA", "1800", "10GB", "700");

        assertEquals("RTX 3080", gpuRequest.getName());
        assertEquals("NVIDIA", gpuRequest.getProducer());
        assertEquals("1800", gpuRequest.getBoostClock());
        assertEquals("10GB", gpuRequest.getVram());
        assertEquals("700", gpuRequest.getPrice());
    }

    @Test
    void testSetAdditionalInfo() {
        gpuRequest.setAdditionalInfo(1, 1, 1, "300", 0);

        assertEquals(1, gpuRequest.getHdmi());
        assertEquals(1, gpuRequest.getDisplayPort());
        assertEquals(1, gpuRequest.getDvi());
        assertEquals("300", gpuRequest.getTdp());
        assertEquals(0, gpuRequest.getVga());
    }

    @Test
    void testParseInteger() {
        assertEquals(100, gpuRequest.parseInteger("100 MHz"));
        assertEquals(0, gpuRequest.parseInteger(null));
        assertEquals(0, gpuRequest.parseInteger("abc"));
        assertEquals(0, gpuRequest.parseInteger(""));
    }

    @Test
    void testParseFloatNumber() {
        assertEquals(100.0f, gpuRequest.parseFloatNumber("100 MHz"));
        assertTrue(gpuRequest.parseFloatNumber(null) > 0);  // случайный результат, но больше 0
        assertTrue(gpuRequest.parseFloatNumber("abc") > 0);  // случайный результат, но больше 0
        assertTrue(gpuRequest.parseFloatNumber("") > 0);  // случайный результат, но больше 0
    }

    @Test
    void testToEntity() {
        gpuRequest.setBaseInfo("RTX 3080", "NVIDIA", "1800", "10GB", "700");
        gpuRequest.setAdditionalInfo(1, 1, 1, "300", 0);

        Gpu gpu = gpuRequest.toEntity();

        assertEquals("RTX 3080", gpu.getName());
        assertEquals("NVIDIA", gpu.getProducer());
        assertEquals(1800, gpu.getBoostClock());
        assertEquals(10, gpu.getVram());
        assertEquals(300, gpu.getTdp());
        assertEquals(1, gpu.getHdmi());
        assertEquals(1, gpu.getDisplayPort());
        assertEquals(1, gpu.getDvi());
        assertEquals(0, gpu.getVga());
        assertEquals(700.0f, gpu.getPrice());
    }
}