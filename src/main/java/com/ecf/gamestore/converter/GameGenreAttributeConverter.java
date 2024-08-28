package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.GameGenre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class GameGenreAttributeConverter implements AttributeConverter<GameGenre, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GameGenre gameGenre) {

        return Objects.isNull(gameGenre) ? null : gameGenre.getKey();
    }

    @Override
    public GameGenre convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? null : GameGenre.getByKey(key.intValue());
    }
}
