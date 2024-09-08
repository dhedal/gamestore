package com.ecf.gamestore.dto;

import java.util.List;
import java.util.Objects;

public class GameFilterDTO {

    private Integer platformKey;
    private List<Integer> genreKeys;
    private Double priceMin;
    private Double priceMax;
    private Integer page;
    private Integer limit;

    public Integer getPlatformKey() {
        return platformKey;
    }

    public void setPlatformKey(Integer platformKey) {
        this.platformKey = platformKey;
    }

    public List<Integer> getGenreKeys() {
        return genreKeys;
    }

    public void setGenreKeys(List<Integer> genreKeys) {
        this.genreKeys = genreKeys;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
