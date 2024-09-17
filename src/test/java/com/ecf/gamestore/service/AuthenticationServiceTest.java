package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.embeddables.Address;
import com.ecf.gamestore.models.enumerations.Role;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceTest.class);

    private AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GSUserService gsUserService;
    private String password = "ECF++2test++3";
    @Autowired
    public AuthenticationServiceTest(
            AuthenticationService authenticationService,
            BCryptPasswordEncoder passwordEncoder,
            GSUserService gsUserService) {
        this.authenticationService = authenticationService;
        this.gsUserService = gsUserService;
        this.passwordEncoder = passwordEncoder;
        System.out.println("--->" + this.passwordEncoder.encode(this.password));
    }

    @Test
    public void test_authentication_NullRequest_ShouldThrowIllegalArgumentException() {
        SigninRequest request = null;
        SigninResponse response = new SigninResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.authentication(request, response);
        });

        assertEquals("SigninRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_authentication_NullResponse_ShouldThrowIllegalArgumentException() {
        SigninRequest request = new SigninRequest();
        SigninResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.authentication(request, response);
        });

        assertEquals("SigninResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_authentication_InvalidRequest() {
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
    public void test_authentication_BadEmail() {
        SigninRequest request = new SigninRequest();
        request.setEmail("admin@email.gs.fr");
        request.setPassword("GS2++test3++");
        List<String> tests = new ArrayList<>();

        tests.add("L'email est introuvable.");
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
    }

    @Test
    public void test_authentication_BadPassword() {
        SigninRequest request = new SigninRequest();
        request.setEmail("admin@email.gs.com");
        request.setPassword("GS2++test3++");
        List<String> tests = new ArrayList<>();

        tests.add("le mot de passe ne correspond pas.");
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), tests);
    }

    @Test
    public void test_authentication_success() {
        SigninRequest request = new SigninRequest();
        request.setEmail("admin@email.gs.com");
        request.setPassword(this.password);
        SigninResponse response = this.signinResponseTest(this.authenticationService.authentication(request, new SigninResponse()), new ArrayList<>());
        this.signinResponseUserDTOTest(request, response);
    }

    ///////////////////////////////////////////////////////////////////////////

    @Test
    public void test_save_NullRequest_ShouldThrowIllegalArgumentException() {
        SignupRequest request = null;
        SignupResponse response = new SignupResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.save(request, response);
        });

        assertEquals("SignupRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_save_NullResponse_ShouldThrowIllegalArgumentException() {
        SignupRequest request = new SignupRequest();
        SignupResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.authenticationService.save(request, response);
        });

        assertEquals("SignupResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_save_InvalidRequest() {
        SignupRequest request = this.createSignupRequest();
        List<String> tests = new ArrayList<>();

        request.setFirstName("");
        tests.add("Le prénom est obligatoire");
        tests.add("le prénom doit avoir entre 3 et 50 charactères");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setLastName("");
        tests.add("Le nom est obligatoire");
        tests.add("le nom doit avoir entre 3 et 50 charactères");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setEmail("");
        tests.add("L'email' est obligatoire");
        tests.add("le format d'email est invalide. ex : xxxx@xxx.xxx");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setPassword("");
        tests.add("Le mot de passe est obligatoire");
        tests.add("Le mot de passe doit avoir au moins 8 caractères.");
        tests.add("Le mot de passe doit contenir à la fois des majuscules et des minuscules.");
        tests.add("Le mot de passe doit contenir au moins un chiffre");
        tests.add("Le mot de passe doit contenir au moins un caractère spécial");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setStreetAddress("");
        tests.add("L'adresse est obligatoire");
        tests.add("l'adresse ne doit contenir pas plus de 100 charactères");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setZipCode("");
        tests.add("Le code postal est obligatoire");
        tests.add("Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setZipCode("125");
        tests.add("Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setCity("");
        tests.add("La ville est obligatoire");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();

        request = this.createSignupRequest();
        request.setCountry("");
        tests.add("Le pays est obligatoire");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();
    }

    @Test
    public void test_save_emailNotUnique() {
        SignupRequest request = this.createSignupRequest();
        List<String> tests = new ArrayList<>();

        request.setEmail("admin@email.gs.com");
        tests.add("L'email existe déjà.");
        this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        tests.clear();
    }

    @Test
    public void test_save_success() {
        SignupRequest request = this.createSignupRequest();
        this.deleteIfExist(request.getEmail());

        this.deleteIfExist(request.getEmail());
        List<String> tests = new ArrayList<>();

        SignupResponse response = this.signupResponseTest(this.authenticationService.save(request, new SignupResponse()), tests);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertTrue(response.isEmailSent());

        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail(request.getEmail());
        signinRequest.setPassword(request.getPassword());

        SigninResponse signinResponse = this.signinResponseTest(this.authenticationService.authentication(signinRequest, new SigninResponse()), tests);
        GSUserDTO userDTO = this.signinResponseUserDTOTest(signinRequest, signinResponse);

        assertEquals(request.getFirstName(), userDTO.getFirstName());
        assertEquals(request.getLastName(), userDTO.getLastName());

        Address address = userDTO.getAddress();
        assertEquals(request.getStreetAddress(), address.getStreetAddress());
        assertEquals(request.getZipCode(), address.getZipCode());
        assertEquals(request.getCity(), address.getCity());
        assertEquals(request.getCountry(), address.getCountry());

        GSUser user = this.gsUserService.getByEmail(request.getEmail());
        assertNotNull(user);
        assertEquals(user.getUuid(), userDTO.getUuid());
        assertEquals(user.getRole(), userDTO.getRole());
    }


    ////////////////////////////////////////////////////////////////////////////

    private SigninResponse signinResponseTest(SigninResponse response, List<String> tests) {
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

    private GSUserDTO signinResponseUserDTOTest(SigninRequest request, SigninResponse response) {
        assertNotNull(request);
        assertNotNull(response);
        GSUserDTO dto = response.getUser();
        assertNotNull(dto);
        assertEquals(request.getEmail(), dto.getEmail());
        return dto;
    }

    private SignupResponse signupResponseTest(SignupResponse response, List<String> tests) {
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

    public SignupRequest createSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setFirstName("Mike");
        request.setLastName("Pondsmith");
        request.setEmail("%s.%s@email.gs".formatted(request.getFirstName(), request.getLastName()));
        request.setPassword(this.password);
        request.setRole(Role.USER.getKey());
        request.setStreetAddress("4 bd voltaire");
        request.setZipCode("75011");
        request.setCity("Paris");
        request.setCountry("France");

        return request;
    }

    public void deleteIfExist(String email) {
        if(email == null || email.isEmpty()) return;
        GSUser user = this.gsUserService.getByEmail(email);
        if(Objects.isNull(user)) return;
        try {
            this.gsUserService.delete(user.getId());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
