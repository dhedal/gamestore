package com.ecf.gamestore.service;

import com.ecf.gamestore.models.OrderLine;
import com.ecf.gamestore.repository.OrderLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLineService extends AbstractService<OrderLineRepository, OrderLine>{
    private static final Logger LOG = LoggerFactory.getLogger(OrderLineService.class);

    @Autowired
    public OrderLineService(OrderLineRepository repository){
        super(repository);
    }
}
