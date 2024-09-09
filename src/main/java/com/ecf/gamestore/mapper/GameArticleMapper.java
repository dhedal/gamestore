package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.dto.PromotionDTO;
import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameArticleMapper {
    public static GameArticleDTO toDTO(GameArticle gameArticle) {
        if(Objects.isNull(gameArticle)) return null;
        GameArticleDTO dto = new GameArticleDTO();
        dto.setUuid(gameArticle.getUuid());
        dto.setPrice(gameArticle.getPrice());
        dto.setStock(gameArticle.getStock());
        dto.setPlatform(gameArticle.getPlatform());
        dto.setGameInfo(GameInfoMapper.toDTO(gameArticle.getGameInfo()));
        return dto;
    }

    public static GameArticleDTO toDTO(Promotion promotion) {
        if(Objects.isNull(promotion)) return null;
        PromotionDTO promotionDTO = PromotionMapper.toDTO(promotion);
        System.out.println(promotionDTO);
        GameArticleDTO dto = promotionDTO.getGameArticle();
        dto.setPromotion(promotionDTO);
        dto.getPromotion().setGameArticle(null);
        return dto;
    }

    public static List<GameArticleDTO> toDTOs(List<GameArticle> gameArticles){
        if(CollectionUtils.isNullOrEmpty(gameArticles)) return List.of();
        return gameArticles.stream()
                .map(GameArticleMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<GameArticleDTO> promotionsToGameArticles(List<Promotion> promotions) {
        if(CollectionUtils.isNullOrEmpty(promotions)) return List.of();
        return promotions.stream()
                .map(GameArticleMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Map<String, GameArticleDTO> promotionsAndGameArticlesToMapGameArticleDTO(List<Promotion> promotions, List<GameArticle> gameArticles) {
        Map<String, GameArticleDTO> dtoMap = new HashMap<>();

        if(Objects.nonNull(promotions)) {
            promotions.forEach(promotion -> {
                GameArticleDTO dto = toDTO(promotion);
                if(Objects.nonNull(dto) && !dtoMap.containsKey(dto.getUuid())) {
                    dtoMap.put(dto.getUuid(), dto);
                }
            });
        }

        if(Objects.nonNull(gameArticles)) {
            gameArticles.forEach(gameArticle -> {
                GameArticleDTO dto = toDTO(gameArticle);
                if(Objects.nonNull(dto) && !dtoMap.containsKey(dto.getUuid())) {
                    dtoMap.put(dto.getUuid(), dto);
                }
            });
        }

        return dtoMap;
    }

}
