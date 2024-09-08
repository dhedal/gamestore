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
    @Query("""
    SELECT ga FROM GameArticle ga 
    WHERE ga.stock > 0 
    AND ga.platform = :platform 
    AND ga.price BETWEEN :priceMin AND :priceMax 
    ORDER BY ga.createdAt DESC
    """)
    public List<GameArticle> findByPlatformAndPriceBetween(Platform platform, Double priceMin, Double priceMax, Pageable pageable);

    @Query("""
    SELECT ga FROM GameArticle ga 
    WHERE ga.stock > 0 
    AND ga.platform = :platform 
    AND ga.gameInfo IN (:gameInfos) 
    AND ga.price BETWEEN :priceMin AND :priceMax
    ORDER BY ga.createdAt DESC
    """)
    public List<GameArticle> findByPlatformAndGameInfosAndPriceBetween(Platform platform, List<GameInfo> gameInfos,  Double priceMin, Double priceMax,Pageable pageable);

    @Query("SELECT ga.platform, COUNT(ga) FROM GameArticle ga GROUP BY ga.platform")
    public List<Object[]> countGameArticleByPlatforms(Platform [] platforms);

}

