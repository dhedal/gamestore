package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByUuid(String uuid);
}
