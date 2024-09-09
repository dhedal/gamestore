package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("""
    SELECT p FROM Promotion p 
    where :date BETWEEN p.startDate AND p.endDate 
    AND p.gameArticle IN (:gameArticles)
    """)
    public List<Promotion> findByGameArticlesAndDate(List<GameArticle> gameArticles, LocalDate date);

}
