package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Product findById(long productId);

    @Query("SELECT g FROM Product g where g.motherBoard IS NOT NULL")
    List<Product> getMotherboards(Pageable pageable);

    @Query("SELECT g FROM Product g where "
            + "g.gpu IS NOT NULL AND "
            + "(:producer IS NULL OR g.gpu.producer = :producer) AND "
            + "(:boostClock IS NULL OR g.gpu.boostClock > :boostClock) AND "
            + "(:displayPort IS NULL OR g.gpu.displayPort > :displayPort) AND "
            + "(:hdmi IS NULL OR g.gpu.hdmi > :hdmi) AND "
            + "(:tdp IS NULL OR g.gpu.tdp > :tdp) AND "
            + "(:vram IS NULL OR g.gpu.vram > :vram)")
    List<Product> findGpuByParams(@Param("producer") String producer,
                                  @Param("boostClock") Integer boostClock,
                                  @Param("displayPort") Integer displayPort,
                                  @Param("hdmi") Integer hdmi,
                                  @Param("tdp") Integer tdp,
                                  @Param("vram") Integer vram, Pageable pageable);


    int countByGpuIsNotNullAndNameContainingIgnoreCase(String name);

    int countByMotherBoardIsNotNullAndNameContainingIgnoreCase(String name);

    int countAllByNameContainingIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
