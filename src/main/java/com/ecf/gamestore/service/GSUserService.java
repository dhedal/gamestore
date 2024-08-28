package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.repository.GSUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GSUserService extends AbstractService<GSUserRepository, GSUser> {
    private static final Logger LOG = LoggerFactory.getLogger(GSUserService.class);

    @Autowired
    public GSUserService(GSUserRepository repository){
        super(repository);
    }
}
