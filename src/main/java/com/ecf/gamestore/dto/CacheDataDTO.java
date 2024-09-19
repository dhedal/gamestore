package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import com.ecf.gamestore.models.enumerations.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheDataDTO {

    private Platform [] platforms = Platform.values();
    private GameGenre [] genres = GameGenre.values();
    private Map<Integer, Integer> gameCountMap = new HashMap<>();
    private OrderStatus [] orderStatus = OrderStatus.values();

    public Platform[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Platform[] platforms) {
        this.platforms = platforms;
    }

    public GameGenre[] getGenres() {
        return genres;
    }

    public void setGenres(GameGenre[] genres) {
        this.genres = genres;
    }

    public Map<Integer, Integer> getGameCountMap() {
        return gameCountMap;
    }

    public void setGameCountMap(Map<Integer, Integer> gameCountMap) {
        this.gameCountMap = gameCountMap;
    }

    public OrderStatus[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus[] orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void parseAndSetGameCountMap(Map<Platform, Long> gameCountMap) {
        Map<Integer, Integer> map  = new HashMap<>();
        gameCountMap.forEach((platform, count) -> {
            map.put(platform.getKey(), count.intValue());
        });
        this.setGameCountMap(map);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CacheDataDTO{");
        sb.append("platforms=").append(Arrays.toString(platforms));
        sb.append(", genres=").append(Arrays.toString(genres));
        sb.append(", gameCountMap=").append(gameCountMap);
        sb.append('}');
        return sb.toString();
    }
}
