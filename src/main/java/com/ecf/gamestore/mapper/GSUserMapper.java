package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.GSUserDTO;
import com.ecf.gamestore.dto.SignupRequest;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.embeddables.Address;
import com.ecf.gamestore.models.enumerations.Role;

import java.util.Objects;

public class GSUserMapper {

    public static GSUserDTO toDTO(GSUser user){
        if(Objects.isNull(user)) return null;
        GSUserDTO dto = new GSUserDTO();
        dto.setUuid(user.getUuid());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public static GSUser toGSUser(SignupRequest request) {
        if(Objects.isNull(request)) return null;
        GSUser user = new GSUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.getByKey(request.getRole()));

        Address address = new Address();
        address.setStreetAddress(request.getStreetAddress());
        address.setZipCode(request.getZipCode());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        return user;
    }
}
