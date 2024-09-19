package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.enumerations.OrderStatus;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private String uuid;
    private LocalDate pickupDate;
    private OrderStatus status;
    private GSUserDTO user;
    private AgenceDTO agence;

    private List<OrderLineDTO> orderLines;
    private LocalDateTime createdAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public GSUserDTO getUser() {
        return user;
    }

    public void setUser(GSUserDTO user) {
        this.user = user;
    }

    public AgenceDTO getAgence() {
        return agence;
    }

    public void setAgence(AgenceDTO agence) {
        this.agence = agence;
    }

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDTO> orderLines) {
        this.orderLines = orderLines;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
