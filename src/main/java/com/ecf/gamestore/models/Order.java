package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.OrderStatus;
import com.ecf.gamestore.models.interfaces.IEntity;
import com.ecf.gamestore.utils.CollectionUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "orders")
public class Order implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false)
    private LocalDate pickupDate;
    @Column(nullable = false)
    private OrderStatus status;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private GSUser user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderLine> orderLines;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
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

    public GSUser getUser() {
        return user;
    }

    public void setUser(GSUser user) {
        this.user = user;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public List<OrderLine> getOrderLines() {
        if(CollectionUtils.isNullOrEmpty(this.orderLines)) return List.of();
        return Collections.unmodifiableList(this.orderLines);
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
