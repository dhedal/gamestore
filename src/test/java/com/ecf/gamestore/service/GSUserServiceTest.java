package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.mapper.GSUserMapper;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.embeddables.Address;
import com.ecf.gamestore.models.enumerations.Role;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class GSUserServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(GSUserServiceTest.class);

    private final GSUserService gsUserService;

    @Autowired
    public GSUserServiceTest(GSUserService gsUserService) {
        this.gsUserService = gsUserService;
    }

    @Test
    public void test_updateUserInfo_NullRequest_ShouldThrowIllegalArgumentException() {
        UserInfoRequest request = null;
        UserInfoResponse response = new UserInfoResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.gsUserService.updateUserInfo(request, response);
        });

        assertEquals("UserInfoRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_updateUserInfo_NullResponse_ShouldThrowIllegalArgumentException() {
        UserInfoRequest request = new UserInfoRequest();
        UserInfoResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.gsUserService.updateUserInfo(request, response);
        });

        assertEquals("UserInfoResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_updateUserInfo_InvalidRequest() {
        UserInfoRequest request = this.createUserInfoRequest();
        List<String> tests = new ArrayList<>();

        request.setUuid("");
        tests.add("L'uuid est obligatoire");
        tests.add("L'uuid doit être valide");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request.setUuid("qsdfsf-qsfqsf-qsfsf");
        tests.add("L'uuid doit être valide");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setFirstName("");
        tests.add("Le prénom est obligatoire");
        tests.add("le prénom doit avoir entre 3 et 50 charactères");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setLastName("");
        tests.add("Le nom est obligatoire");
        tests.add("le nom doit avoir entre 3 et 50 charactères");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setStreetAddress("");
        tests.add("L'adresse est obligatoire");
        tests.add("l'adresse ne doit contenir pas plus de 100 charactères");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setZipCode("");
        tests.add("Le code postal est obligatoire");
        tests.add("Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request.setZipCode("7501");
        tests.add("Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setCity("");
        tests.add("La ville est obligatoire");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();

        request = this.createUserInfoRequest();
        request.setCountry("");
        tests.add("Le pays est obligatoire");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();
    }

    @Test
    public void test_updateUserInfo_UserNotFound() {
        UserInfoRequest request = this.createUserInfoRequest();
        List<String> tests = new ArrayList<>();
        tests.add("l'utilisateur est introuvable");
        tests.add("Déconnectez vous du site, puis reconnectez vous");
        this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
        tests.clear();
    }

    @Test
    public void test_updateUserInfo_success() {
        try {
            GSUser user = this.createGSUser();
            GSUserDTO userDTO = GSUserMapper.toDTO(user);

            List<String> tests = new ArrayList<>();

            UserInfoRequest request = this.createUserInfoRequest();

            this.isUserEquals(request, userDTO, false);

            request.setUuid(userDTO.getUuid());

            UserInfoResponse response = this.userInfoResponseTest(this.gsUserService.updateUserInfo(request, new UserInfoResponse()), tests);
            assertTrue(response.isOk());
            assertTrue(response.getMessages().isEmpty());
            this.isUserEquals(
                    request,
                    response.getUser(),
                    true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private UserInfoRequest createUserInfoRequest() {
        UserInfoRequest request = new UserInfoRequest();
        request.setUuid(UUID.randomUUID().toString());
        request.setFirstName("john");
        request.setLastName("doe");
        request.setStreetAddress("5 bv du jeux vidéo");
        request.setZipCode("15247");
        request.setCity("Lille");
        request.setCountry("France");
        return request;
    }

    private UserInfoResponse userInfoResponseTest(UserInfoResponse response, List<String> tests) {
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

    private GSUser createGSUser() throws Throwable {
        String email = "email@test.gs";
        if(this.gsUserService.isEmailExist(email)) {
            this.gsUserService.delete(this.gsUserService.getByEmail(email).getId());
        }
        GSUser user = new GSUser();
        user.setFirstName("peter");
        user.setLastName("parker");
        user.setEmail(email);
        user.setPassword("ECF++2test++3");
        user.setRole(Role.USER);
        Address address = new Address();
        address.setStreetAddress("1 rue du jeux video");
        address.setZipCode("75011");
        address.setCity("Nice");
        address.setCountry("Espagne");
        user.setAddress(address);
        return this.gsUserService.save(user);
    }

    private void isUserEquals(UserInfoRequest request, GSUserDTO dto, boolean result) {
        assertTrue(Objects.equals(request.getUuid(), dto.getUuid()) == result);
        assertTrue(Objects.equals(request.getFirstName(), dto.getFirstName()) == result);
        assertTrue(Objects.equals(request.getLastName(), dto.getLastName()) == result);
        Address address = dto.getAddress();
        assertTrue(Objects.equals(request.getStreetAddress(), address.getStreetAddress()) == result);
        assertTrue(Objects.equals(request.getZipCode(), address.getZipCode()) == result);
        assertTrue(Objects.equals(request.getCity(), address.getCity()) == result);
        assertTrue(Objects.equals(request.getCountry(), address.getCountry()) == result);
    }
}
