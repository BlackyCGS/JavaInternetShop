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
        gpuRequest.setBaseInfo("RTX 3090", "NVIDIA", "1800", "24GB", "1500.99");

        assertEquals("RTX 3090", gpuRequest.getName());
        assertEquals("NVIDIA", gpuRequest.getProducer());
        assertEquals("1800", gpuRequest.getBoostClock());
        assertEquals("24GB", gpuRequest.getVram());
        assertEquals("1500.99", gpuRequest.getPrice());
    }

    @Test
    void testSetAdditionalInfo() {
        gpuRequest.setAdditionalInfo(3, 1, 2, "350W", 0);

        assertEquals(3, gpuRequest.getDisplayPort());
        assertEquals(1, gpuRequest.getDvi());
        assertEquals(2, gpuRequest.getHdmi());
        assertEquals("350W", gpuRequest.getTdp());
        assertEquals(0, gpuRequest.getVga());
    }

    @Test
    void testParseInteger_withValidValue() {
        Integer parsedValue = gpuRequest.parseInteger("1800MHz");
        assertEquals(1800, parsedValue);
    }

    @Test
    void testParseInteger_withEmptyString() {
        Integer parsedValue = gpuRequest.parseInteger("");
        assertEquals(0, parsedValue);
    }

    @Test
    void testParseInteger_withNull() {
        Integer parsedValue = gpuRequest.parseInteger(null);
        assertEquals(0, parsedValue);
    }

    @Test
    void testParseFloatNumber_withValidValue() {
        Float parsedValue = gpuRequest.parseFloatNumber("1500.99");
        assertEquals(1500.99, parsedValue, 0.01);
    }

    @Test
    void testParseFloatNumber_withEmptyString() {
        Float parsedValue = gpuRequest.parseFloatNumber("");
        assertTrue(parsedValue >= 0 && parsedValue <= 2000);
    }

    @Test
    void testParseFloatNumber_withNull() {
        Float parsedValue = gpuRequest.parseFloatNumber(null);
        assertTrue(parsedValue >= 0 && parsedValue <= 2000);
    }

    @Test
    void testToEntity() {
        gpuRequest.setBaseInfo("RTX 3090", "NVIDIA", "1800", "24GB", "1500.99");
        gpuRequest.setAdditionalInfo(3, 1, 2, "350W", 0);

        Gpu gpu = gpuRequest.toEntity();

        assertEquals("RTX 3090", gpu.getName());
        assertEquals("NVIDIA", gpu.getProducer());
        assertEquals(1800, gpu.getBoostClock());
        assertEquals(24, gpu.getVram());
        assertEquals(350, gpu.getTdp());
        assertEquals(2, gpu.getHdmi());
        assertEquals(3, gpu.getDisplayPort());
        assertEquals(1, gpu.getDvi());
        assertEquals(0, gpu.getVga());
        assertEquals(1500.99, gpu.getPrice(), 0.01);
    }
}
