package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.enumerations.Platform;

public class GameArticleDTO {
    private String uuid;
    private double price = 0d;
    private double stock = 0d;
    private Platform platform;
    private GameInfoDTO gameInfo;


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

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public GameInfoDTO getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfoDTO gameInfo) {
        this.gameInfo = gameInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameArticleDTO{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", price=").append(price);
        sb.append(", stock=").append(stock);
        sb.append(", platform=").append(platform);
        sb.append(", gameInfo=").append(gameInfo);
        sb.append('}');
        return sb.toString();
    }
}
