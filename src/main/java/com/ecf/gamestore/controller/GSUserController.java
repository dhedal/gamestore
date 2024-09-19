package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.UserInfoRequest;
import com.ecf.gamestore.dto.UserInfoResponse;
import com.ecf.gamestore.service.AuthenticationService;
import com.ecf.gamestore.service.GSUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class GSUserController {
    private static final Logger LOG = LoggerFactory.getLogger(GSUserController.class);

    private GSUserService gsUserService;

    @Autowired
    public GSUserController(@Lazy GSUserService gsUserService) {
        this.gsUserService = gsUserService;
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> update(@RequestBody UserInfoRequest request) {
        LOG.debug("## update(@RequestBody UserInfoRequest request)");
        final UserInfoResponse response = new UserInfoResponse();
        try {
            this.gsUserService.updateUserInfo(request, response);
        } catch (Exception e){
            LOG.error(e.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }
}
