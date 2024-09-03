package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameArticleRepository extends JpaRepository<GameArticle, Long> {
    public GameArticle findByUuid(String uuid);
}

