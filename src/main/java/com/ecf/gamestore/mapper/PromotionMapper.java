package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.PromotionDTO;
import com.ecf.gamestore.models.Promotion;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PromotionMapper {
    public static PromotionDTO toDTO(Promotion promotion) {
        if(Objects.isNull(promotion)) return null;

        PromotionDTO dto = new PromotionDTO();
        dto.setUuid(promotion.getUuid());
        dto.setDiscountRate(promotion.getDiscountRate());
        dto.setStartDate(promotion.getStartDate());
        dto.setEndDate(promotion.getEndDate());
        dto.setGameArticle(GameArticleMapper.toDTO(promotion.getGameArticle()));
        return dto;
    }

    public static List<PromotionDTO> toDTOs(List<Promotion> promotions) {
        if(Objects.isNull(promotions)) return List.of();
        return promotions.stream()
                .map(PromotionMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
