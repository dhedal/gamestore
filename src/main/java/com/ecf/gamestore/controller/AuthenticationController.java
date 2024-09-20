package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SigninResponse> authenticate(@RequestBody SigninRequest request) {
        LOG.debug("## authenticate (SigninRequest request");
        final SigninResponse response = new SigninResponse();
        try {
            this.authenticationService.authentication(request, response);
        } catch (Exception ex) {
            LOG.error(ex.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> register(@RequestBody SignupRequest request) {
        LOG.debug("## register(SignupRequest request)");
        final SignupResponse response = new SignupResponse();
        try {
            this.authenticationService.save(request, response);
        } catch (Exception e){
            LOG.error(e.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SigninResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        LOG.debug("## changePassword (ChangePasswordRequest request");
        final SigninResponse response = new SigninResponse();
        try {
            this.authenticationService.changePassword(request, response);
        } catch (Exception ex) {
            LOG.error(ex.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        }


        return ResponseEntity.ok(response);
    }

}
