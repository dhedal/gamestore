package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.GameGenre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GameGenreListAttributeConverter implements AttributeConverter<List<GameGenre>, String> {
    @Override
    public String convertToDatabaseColumn(List<GameGenre> gameGenres) {
        return Objects.isNull(gameGenres) || gameGenres.isEmpty() ?
                "0" :
                gameGenres.stream()
                        .map(GameGenre::getKey)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
    }

    @Override
    public List<GameGenre> convertToEntityAttribute(String s) {
        return Objects.isNull(s) || s.isEmpty() ?
                List.of(GameGenre.UNDEFINED) :
                Stream.of(s.split(","))
                        .filter(this::checkData)
                        .map(Integer::valueOf)
                        .map(GameGenre::getByKey)
                        .collect(Collectors.toList());

    }

    private boolean checkData(String data) {
        return Objects.nonNull(data) &&
                !data.isEmpty() &&
                data.trim().chars().allMatch(Character::isDigit);
    }
}
