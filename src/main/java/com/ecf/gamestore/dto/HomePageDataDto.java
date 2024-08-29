package com.ecf.gamestore.dto;

import java.util.ArrayList;
import java.util.List;

public class HomePageDataDto {

    private List<GameVideoDto> lastGames = new ArrayList<>();
    private List<GameVideoDto> currentPromotions = new ArrayList<>();

    public List<GameVideoDto> getLastGames() {
        return lastGames;
    }

    public void setLastGames(List<GameVideoDto> lastGames) {
        this.lastGames = lastGames;
    }

    public List<GameVideoDto> getCurrentPromotions() {
        return currentPromotions;
    }

    public void setCurrentPromotions(List<GameVideoDto> currentPromotions) {
        this.currentPromotions = currentPromotions;
    }
}
