package com.ecf.gamestore.dto;

import java.util.List;
import java.util.Map;

public class CartPageDataDTO {

    private Map<String, Integer> stockMap = Map.of();
    private List<AgenceDTO> agences = List.of();

    public Map<String, Integer> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<String, Integer> stockMap) {
        this.stockMap = stockMap;
    }

    public List<AgenceDTO> getAgences() {
        return agences;
    }

    public void setAgences(List<AgenceDTO> agences) {
        this.agences = agences;
    }
}
