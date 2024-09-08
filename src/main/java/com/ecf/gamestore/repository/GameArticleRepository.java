package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
public interface GameArticleRepository extends JpaRepository<GameArticle, Long> {
    public GameArticle findByUuid(String uuid);
    public List<GameArticle> findByPlatform(Platform platform, Pageable pageable);
    public List<GameArticle> findByPlatformAndGameInfoIn(Platform platform, List<GameInfo> gameInfos, Pageable pageable);

    @Query("SELECT ga.platform, COUNT(ga) FROM GameArticle ga GROUP BY ga.platform")
    public List<Object[]> countGameArticleByPlatforms(Platform [] platforms);

}

