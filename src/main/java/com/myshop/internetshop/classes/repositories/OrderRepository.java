package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);

    @Query(
            value = "SELECT o.* FROM orders o  INNER JOIN "
                    + "users a ON o.user_id = a.id "
                    + "WHERE (:id IS NULL OR a.id = :id) AND "
                    + "(:status IS NULL OR o.order_status = :status)",
        nativeQuery = true)
    List<Order> findByOrderStatus(@Param("id") Integer id,
                                  @Param("status") Integer status);
}
