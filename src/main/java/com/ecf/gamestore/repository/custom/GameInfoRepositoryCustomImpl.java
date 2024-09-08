package com.ecf.gamestore.repository.custom;

import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class GameInfoRepositoryCustomImpl implements GameInfoRepositoryCustom{
    private static final Logger LOG = LoggerFactory.getLogger(GameInfoRepositoryCustomImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<GameInfo> findByPlatformAndGenresDynamic(Platform platform, List<GameGenre> genres) {
        if(Objects.isNull(platform) || Objects.isNull(genres) || genres.isEmpty()) return List.of();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM game_info WHERE find_in_set('%s', platforms) AND (%s)");
        String query = sb.toString().formatted(
                platform.getKey(),
                genres.stream()
                        .map(GameInfoRepositoryCustomImpl::findInSetSQLFormat)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(" OR "))
        );
        LOG.debug(query);

        Query nativeQuery = this.entityManager.createNativeQuery(query, GameInfo.class);
        return nativeQuery.getResultList();
    }

    private static String findInSetSQLFormat(GameGenre gameGenre) {
        return "find_in_set('%s', genres) > 0".formatted(gameGenre.getKey());
    }
}
