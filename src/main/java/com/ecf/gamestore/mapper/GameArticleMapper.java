package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.models.GameArticle;

import java.util.List;
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

    public static List<GameArticleDTO> toDTOs(List<GameArticle> gameArticles){
        if(Objects.isNull(gameArticles)) return List.of();
        return gameArticles.stream()
                .map(GameArticleMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
