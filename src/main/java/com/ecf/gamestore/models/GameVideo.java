package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "game_video",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"game_video_meta_id", "platform"})}
)
public class GameVideo implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    private double price = 0d;
    @ManyToOne(optional = false)
    @JoinColumn(name="gameVideoMeta_id", nullable = false)
    private GameVideoMeta gameVideoMeta;
    @Column(nullable = false)
    private Platform platform;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public GameVideoMeta getGameVideoMeta() {
        return gameVideoMeta;
    }

    public void setGameVideoMeta(GameVideoMeta gameVideoMeta) {
        this.gameVideoMeta = gameVideoMeta;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
