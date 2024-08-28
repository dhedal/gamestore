package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.Pegi;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class PegiAttributeConverter implements AttributeConverter<Pegi, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Pegi pegi) {
        return Objects.isNull(pegi) ? null : pegi.getKey();
    }

    @Override
    public Pegi convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? null : Pegi.getByKey(key.intValue());
    }
}