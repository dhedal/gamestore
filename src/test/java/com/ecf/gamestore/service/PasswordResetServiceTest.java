package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.ForgotPasswordRequest;
import com.ecf.gamestore.dto.ForgotPasswordResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class PasswordResetServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetServiceTest.class);

    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public PasswordResetServiceTest(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Test
    public void test_createToken_NullResponse_ShouldThrowIllegalArgumentException() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        ForgotPasswordResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.passwordResetTokenService.createToken(request, response);
        });

        assertEquals("ForgotPasswordResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_createToken_NullRequest_ShouldThrowIllegalArgumentException() {
        ForgotPasswordRequest request = null;
        ForgotPasswordResponse response = new ForgotPasswordResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.passwordResetTokenService.createToken(request, response);
        });

        assertEquals("ForgotPasswordRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_createToken_ConstraintViolation() {
        ForgotPasswordRequest request = this.createForgotPasswordRequest();
        List<String> tests = new ArrayList<>();

        request.setEmail("");
        tests.add("L'email' est obligatoire");
        tests.add("le format d'email est invalide. ex : xxxx@xxx.xxx");
        this.testForgotPasswordResponse(this.passwordResetTokenService.createToken(request, new ForgotPasswordResponse()), tests);
        tests.clear();

        request.setEmail("qsfsf@qsfsf");
        tests.add("le format d'email est invalide. ex : xxxx@xxx.xxx");
        this.testForgotPasswordResponse(this.passwordResetTokenService.createToken(request, new ForgotPasswordResponse()), tests);
        tests.clear();

        request.setEmail("qsfsf@qsfsf.gs");
        tests.add("Cet email n'existe pas");
        this.testForgotPasswordResponse(this.passwordResetTokenService.createToken(request, new ForgotPasswordResponse()), tests);
        tests.clear();

        request = this.createForgotPasswordRequest();
        ForgotPasswordResponse response = this.testForgotPasswordResponse(this.passwordResetTokenService.createToken(request, new ForgotPasswordResponse()), tests);
        tests.clear();

        assertTrue(response.isOk());
        assertTrue(response.isEmailSent());
    }

    private ForgotPasswordRequest createForgotPasswordRequest() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("admin@email.gs.com");
        return request;
    }

    private ForgotPasswordResponse testForgotPasswordResponse(ForgotPasswordResponse response, List<String> tests) {
        assertNotNull(response);
        List<String> messages = response.getMessages();
        System.out.println();
        messages.forEach(System.out::println);
        assertTrue(messages.isEmpty() == tests.isEmpty());

        for(String test: tests) {
            assertTrue(messages.contains(test));
        }
        return response;
    }
}
