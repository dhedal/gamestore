package com.ecf.gamestore.dto;

public class OrderLineDTO {
    private String uuid;
    private int quantity;
    private String orderUuid;
    private GameArticleDTO gameArticle;
    private PromotionDTO promotion;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public GameArticleDTO getGameArticle() {
        return gameArticle;
    }

    public void setGameArticle(GameArticleDTO gameArticle) {
        this.gameArticle = gameArticle;
    }

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDTO promotion) {
        this.promotion = promotion;
    }
}
