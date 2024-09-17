package com.ecf.gamestore.dto;

import com.ecf.gamestore.utils.CollectionUtils;
import com.ecf.gamestore.validation.constraint.ValidPickupDate;
import com.ecf.gamestore.validation.constraint.ValidUUID;
import com.ecf.gamestore.validation.constraint.ValidUUIDMap;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class OrderRequest {

    @NotBlank(message = "L'user est obligatoire")
    @ValidUUID(message = "L'user doit être un UUID valide")
    private String user;
    @ValidUUIDMap(message = "Les clés de la map doivent être des UUIDs valides et les valeurs doivent être des supérieurs à zéro")
    private Map<String, Integer> articles;

    @NotBlank(message = "L'agence est obligatoire")
    @ValidUUID(message = "L'agence doit être un UUID valide")
    private String agence;

    @NotNull(message = "La date de retrait est obligatoire")
    @ValidPickupDate
    private LocalDate date;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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
        final StringBuilder sb = new StringBuilder("OrderRequest{");
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
