package com.ecf.gamestore.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorConfig implements ErrorController {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorConfig.class);

    @GetMapping("/error")
    public String handleError(HttpServletRequest request){
        LOG.debug("## error");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        LOG.debug("path: " + request.getRequestURI());

        if(status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
//                return "error-404";
                LOG.debug("error-404");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
//                return "error-500";
                LOG.debug("error-500");
            }
            LOG.debug("status-code: " + statusCode);
        }

        LOG.debug("error obj: " + status.toString());

        return "index.html";
    }
}
