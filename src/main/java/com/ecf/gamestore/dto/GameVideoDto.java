package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.GameVideoMeta;
import com.ecf.gamestore.models.enumerations.Platform;

public class GameVideoDto {
    private String uuid;
    private double price = 0d;
    private Platform platform;
    private GameVideoMetaDto gameVideoMeta;
    private PromotionDto promotion;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public GameVideoMetaDto getGameVideoMeta() {
        return gameVideoMeta;
    }

    public void setGameVideoMeta(GameVideoMetaDto gameVideoMeta) {
        this.gameVideoMeta = gameVideoMeta;
    }

    public PromotionDto getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDto promotion) {
        this.promotion = promotion;
    }
}
