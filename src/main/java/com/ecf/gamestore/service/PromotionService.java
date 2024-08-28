package com.ecf.gamestore.service;

import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.repository.PromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PromotionService extends AbstractService<PromotionRepository, Promotion>{
    private static final Logger LOG = LoggerFactory.getLogger(PromotionService.class);

    public PromotionService(PromotionRepository repository){
        super(repository);
    }
}
