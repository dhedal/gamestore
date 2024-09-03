package com.ecf.gamestore.service;

import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.repository.HomePageDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomePageDataService extends AbstractService<HomePageDataRepository, HomePageData> {
    private static final Logger LOG = LoggerFactory.getLogger(HomePageDataService.class);

    @Autowired
    public HomePageDataService(HomePageDataRepository repository) {
        super(repository);
    }

    public HomePageData fetchHomePageData() {
        return this.listAll().getFirst();
    }
}
