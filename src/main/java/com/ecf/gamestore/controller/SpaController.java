package com.ecf.gamestore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {
    private static final Logger LOG = LoggerFactory.getLogger(SpaController.class);

    @GetMapping(value = "/")
    public String index(){
        return "forward:index.html";
    }
}
