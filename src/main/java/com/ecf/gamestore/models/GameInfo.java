package com.ecf.gamestore.models;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Pegi;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.models.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class GameInfo implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;
    @Column(nullable = false, length = 150)
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT", nullable = true)
    private String summary;
    @Column(nullable = false)
    private LocalDate firstReleaseDate;
    @Column(nullable = false)
    private List<GameGenre> genres;
    @Column(nullable = false)
    private Pegi pegi;
    @Column(nullable = false)
    private List<Platform> platforms;
    @Column(nullable = true, length = 250)
    private String coverUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(LocalDate firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public List<GameGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<GameGenre> genres) {
        this.genres = genres;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameInfo{");
        sb.append("id=").append(id);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", summary='").append("summary").append('\'');
        sb.append(", firstReleaseDate=").append(firstReleaseDate);
        sb.append(", genres=").append(genres);
        sb.append(", pegi=").append(pegi);
        sb.append(", platforms=").append(platforms);
        sb.append(", coverUrl='").append(coverUrl).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
