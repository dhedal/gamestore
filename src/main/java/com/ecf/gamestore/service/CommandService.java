package com.ecf.gamestore.service;

import com.ecf.gamestore.models.Command;
import com.ecf.gamestore.repository.CommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandService extends AbstractService<CommandRepository, Command>{
    private static final Logger LOG = LoggerFactory.getLogger(CommandService.class);

    public CommandService(CommandRepository repository){
        super(repository);
    }
}
