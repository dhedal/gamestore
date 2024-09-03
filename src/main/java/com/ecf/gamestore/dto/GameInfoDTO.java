package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Pegi;
import com.ecf.gamestore.models.enumerations.Platform;

import java.time.LocalDate;
import java.util.List;

public class GameInfoDTO {
    private String uuid;
    private String title;
    private String summary;
    private LocalDate firstReleaseDate;
    private List<GameGenre> genres;
    private Pegi pegi;
    private List<Platform> platforms;
    private String coverUrl;

    public String getUuid() {
        return uuid;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameInfoDTO{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", firstReleaseDate=").append(firstReleaseDate);
        sb.append(", genres=").append(genres);
        sb.append(", pegi=").append(pegi);
        sb.append(", platforms=").append(platforms);
        sb.append(", coverUrl='").append(coverUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
