package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.Platform;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class PlatformAttributeConverter implements AttributeConverter<Platform, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Platform platform) {
        return Objects.isNull(platform) ? Platform.NONE.getKey() : platform.getKey();
    }

    @Override
    public Platform convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? Platform.NONE : Platform.getByKey(key.intValue());
    }
}
