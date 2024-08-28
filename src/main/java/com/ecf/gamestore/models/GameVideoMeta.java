package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Pegi;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class GameVideoMeta implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false)
    private GameGenre gameGenre;
    @Column(nullable = false)
    private Pegi pegi;
    @Lob
    @Column(nullable = true)
    private String description;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

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

    public GameGenre getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(GameGenre gameGenre) {
        this.gameGenre = gameGenre;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
