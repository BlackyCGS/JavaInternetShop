package com.myshop.internetshop.entities;

import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Product;
import com.myshop.internetshop.classes.enums.ProductTableId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GpuTest {

    private Gpu gpu;

    @BeforeEach
    void setUp() {
        gpu = new Gpu();
    }

    @Test
    void testSetBaseInfo() {
        gpu.setBaseInfo(1, "RTX 3080", "NVIDIA", 1800, 10);

        assertEquals(1, gpu.getProductId());
        assertEquals("RTX 3080", gpu.getName());
        assertEquals("NVIDIA", gpu.getProducer());
        assertEquals(1800, gpu.getBoostClock());
        assertEquals(10, gpu.getVram());
    }

    @Test
    void testSetAdditionalInfo() {
        gpu.setAdditionalInfo(3, 1, 2, 350, 0, 1500.99f);

        assertEquals(3, gpu.getDisplayPort());
        assertEquals(1, gpu.getDvi());
        assertEquals(2, gpu.getHdmi());
        assertEquals(350, gpu.getTdp());
        assertEquals(0, gpu.getVga());
        assertEquals(1500.99f, gpu.getPrice());
    }

    @Test
    void testParseToProduct() {
        gpu.setBaseInfo(1, "RTX 3080", "NVIDIA", 1800, 10);
        gpu.setAdditionalInfo(3, 1, 2, 350, 0, 1500.99f);

        Product product = gpu.parseToProduct();

        assertNotNull(product);
        assertEquals("RTX 3080", product.getName());
        assertEquals(ProductTableId.GPU.getTableId(), product.getCategoryId());
        assertEquals(1500.99f, product.getPrice());
    }

    @Test
    void testDefaultValues() {
        // Test default values for fields not set
        assertEquals(1000, gpu.getBoostClock());
        assertEquals(1000, gpu.getVram());
        assertEquals(100, gpu.getTdp());
        assertEquals(0, gpu.getHdmi());
        assertEquals(0, gpu.getDisplayPort());
        assertEquals(0, gpu.getDvi());
        assertEquals(0, gpu.getVga());
        assertEquals(0f, gpu.getPrice());
    }
}

