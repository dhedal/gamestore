package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class RoleAttributeConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {

        return Objects.isNull(role) ? Role.USER.getKey() : role.getKey();
    }

    @Override
    public Role convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? Role.USER : Role.getByKey(key.intValue());
    }
}
