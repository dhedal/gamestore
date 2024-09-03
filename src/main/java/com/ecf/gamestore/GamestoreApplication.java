package com.ecf.gamestore;

import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.service.HomePageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GamestoreApplication{

    @Autowired
    private HomePageDataService homePageDataService;

    public static void main(String[] args) {
        SpringApplication.run(GamestoreApplication.class, args);
    }

}
