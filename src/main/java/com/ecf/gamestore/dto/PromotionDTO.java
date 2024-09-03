package com.ecf.gamestore.dto;

import java.time.LocalDate;

public class PromotionDTO {
    private String uuid;
    private double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private GameArticleDTO gameArticle;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public GameArticleDTO getGameArticle() {
        return gameArticle;
    }

    public void setGameArticle(GameArticleDTO gameArticle) {
        this.gameArticle = gameArticle;
    }
}
