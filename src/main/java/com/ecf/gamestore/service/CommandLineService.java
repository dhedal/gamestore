package com.ecf.gamestore.service;

import com.ecf.gamestore.models.CommandLine;
import com.ecf.gamestore.repository.CommandLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandLineService extends AbstractService<CommandLineRepository, CommandLine>{
    private static final Logger LOG = LoggerFactory.getLogger(CommandLineService.class);

    public CommandLineService(CommandLineRepository repository){
        super(repository);
    }
}
