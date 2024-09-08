package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    public List<Promotion> findByGameArticleInAndStartDateAfterAndEndDateBefore(List<GameArticle> gameArticles, LocalDate after, LocalDate before);

    public Promotion findByGameArticleAndStartDateAfterAndEndDateBefore(GameArticle gameArticle, LocalDate after, LocalDate before);
}
