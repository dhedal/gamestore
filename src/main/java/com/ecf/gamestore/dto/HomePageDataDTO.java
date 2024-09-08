package com.ecf.gamestore.dto;

import java.util.ArrayList;
import java.util.List;

public class HomePageDataDTO {

    private String uuid;

    private List<GameArticleDTO> gameArticles = new ArrayList<>();
    private List<GameArticleDTO> promotions = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<GameArticleDTO> getGameArticles() {
        return gameArticles;
    }

    public void setGameArticles(List<GameArticleDTO> gameArticles) {
        this.gameArticles = gameArticles;
    }

    public List<GameArticleDTO> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<GameArticleDTO> promotions) {
        this.promotions = promotions;
    }
}
