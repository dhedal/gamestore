package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.SigninRequest;
import com.ecf.gamestore.dto.SigninResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceTest.class);

    private AuthenticationService authenticationService;
    @Autowired
    public AuthenticationServiceTest(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Test
    public void testAuthentication_NullRequest_ShouldThrowIllegalArgumentException() {
        SigninRequest request = null;
        SigninResponse response = new SigninResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.authentication(request, response);
        });

        assertEquals("SigninRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void testAuthentication_NullResponse_ShouldThrowIllegalArgumentException() {
        SigninRequest request = new SigninRequest();
        SigninResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.authentication(request, response);
        });

        assertEquals("SigninResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void testAuthentication_InvalidRequest() {
        SigninRequest request = new SigninRequest();
        request.setPassword("GS2++test3++");
        List<String> tests = new ArrayList<>();

        request.setEmail("");
        tests.add("L'email' est obligatoire");
        tests.add("le format d'email est invalide. ex : xxxx@xxx.xxx");
        this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
        tests.clear();

        request.setEmail("qsfldq@fsqsdfqsf");
        tests.add("le format d'email est invalide. ex : xxxx@xxx.xxx");
        this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
        tests.clear();

        request.setPassword("");
        tests.add("Le mot de passe est obligatoire");
        tests.add("Le mot de passe doit avoir au moins 8 caractères.");
        tests.add("Le mot de passe doit contenir à la fois des majuscules et des minuscules.");
        tests.add("Le mot de passe doit contenir au moins un chiffre");
        tests.add("Le mot de passe doit contenir au moins un caractère spécial");
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
        tests.clear();
    }


    @Test
    public void testAuthentication_BadEmail() {
        SigninRequest request = new SigninRequest();
        request.setEmail("admin@email.gs.com");
        request.setPassword("GS2++test3++");
        List<String> tests = new ArrayList<>();

        tests.add("L'email est introuvable.");
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
    }

    @Test
    public void testAuthentication_BadPassword() {
        SigninRequest request = new SigninRequest();
        request.setEmail("admin@email.gs.com");
        request.setPassword("GS2++test3++");
        List<String> tests = new ArrayList<>();

        tests.add("le mot de passe ne correspond pas.");
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
    }

    private SigninResponse signinResponseTest(SigninResponse response, List<String> tests) {
        assertNotNull(response);
        List<String> messages = response.getMessages();
        assertFalse(messages.isEmpty());
        System.out.println();
        messages.forEach(System.out::println);
        for(String test: tests) {
            assertTrue(messages.contains(test));
        }
        return response;
    }

    public static SigninRequest createSigninRequest() {
        SigninRequest request = new SigninRequest();
        return request;
    }
}
