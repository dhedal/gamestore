package com.ecf.gamestore.dto;

import java.time.LocalDate;
import java.util.Map;

public class OrderDTO {

    private Map<String,Integer> articles;
    private String agence;
    private LocalDate date;

    public Map<String, Integer> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, Integer> articles) {
        this.articles = articles;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDTO{");
        sb.append("articles=").append(articles);
        sb.append(", agence='").append(agence).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
