package com.ecf.gamestore.dto;

import com.ecf.gamestore.utils.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderDTO {

    private Map<String,Integer> articles;
    private String agence;
    private LocalDate date;

    public Map<String, Integer> getArticles() {
        return CollectionUtils.isNullOrEmpty(this.articles) ? Map.of() : this.articles;
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

    public List<String> getUuids() {
        if(CollectionUtils.isNullOrEmpty(this.articles)) return List.of();
        return this.articles.keySet().stream().toList();
    }

    public boolean isValid() {
        if(CollectionUtils.isNullOrEmpty(this.articles)) return false;
        if(!StringUtils.hasText(this.agence)) return false;
        if(Objects.isNull(this.date)) return false;
        return true;
    }
}
