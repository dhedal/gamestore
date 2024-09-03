package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "game_article",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"game_info_id", "platform"})}
)
public class GameArticle implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    private Double price;
    private Integer stock;
    @Column(nullable = false)
    private Platform platform;
    @ManyToOne(optional = false)
    @JoinColumn(name="gameInfo_id", nullable = false)
    private GameInfo gameInfo;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameArticle{");
        sb.append("id=").append(id);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", price=").append(price);
        sb.append(", stock=").append(stock);
        sb.append(", platform=").append(platform);
        sb.append(", gameInfo=").append(gameInfo);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
