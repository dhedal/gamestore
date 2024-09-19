package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByUuid(String uuid);

    public List<Order> findOrdersByCreatedAtGreaterThanEqual(LocalDateTime createdAt);

    public List<Order> findOrdersByUser(GSUser user);
}
