package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Gpu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Integer> {
    List<Gpu> findByName(String producer);

    Gpu findByProductId(int id);

    @Query("SELECT g FROM Gpu g WHERE "
            + "(:producer IS NULL OR g.producer = :producer) AND "
            + "(:boostClock IS NULL OR g.boostClock > :boostClock) AND "
            + "(:displayPort IS NULL OR g.displayPort > :displayPort) AND "
            + "(:hdmi IS NULL OR g.hdmi > :hdmi) AND "
            + "(:tdp IS NULL OR g.tdp > :tdp) AND "
            + "(:vram IS NULL OR g.vram > :vram)")
    List<Gpu> searchGpu(
            @Param("producer") String producer,
            @Param("boostClock") Integer boostClock,
            @Param("displayPort") Integer displayPort,
            @Param("hdmi") Integer hdmi,
            @Param("tdp") Integer tdp,
            @Param("vram") Integer vram
    );
}