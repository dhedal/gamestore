package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.HomePageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomePageDataRepository extends JpaRepository<HomePageData,Long> {
}
