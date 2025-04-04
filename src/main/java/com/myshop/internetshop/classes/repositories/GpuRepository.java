package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Gpu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Integer> {
    List<Gpu> findByName(String producer);

    Gpu findByProductId(int id);

    void deleteByProductId(int id);
}