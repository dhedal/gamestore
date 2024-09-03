package com.ecf.gamestore.models;

import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class CommandLine implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false)
    private int quantity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "command_id", nullable = false)
    private Command command;
    @ManyToOne(optional = false)
    @JoinColumn(name = "game_video_id", nullable = false)
    private GameArticle gameVideo;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public GameArticle getGameVideo() {
        return gameVideo;
    }

    public void setGameVideo(GameArticle gameVideo) {
        this.gameVideo = gameVideo;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
