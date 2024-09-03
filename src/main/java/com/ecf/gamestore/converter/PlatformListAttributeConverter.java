package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlatformListAttributeConverter implements AttributeConverter<List<Platform>, String> {
    @Override
    public String convertToDatabaseColumn(List<Platform> platforms) {
        return Objects.isNull(platforms) || platforms.isEmpty() ?
                "" :
                platforms.stream()
                        .map(Platform::getKey)
                        .map(String::valueOf)
                        .collect(Collectors.joining("-"));
    }

    @Override
    public List<Platform> convertToEntityAttribute(String s) {
        return Objects.isNull(s) || s.isEmpty() ?
                List.of() :
                Stream.of(s.split("-"))
                        .filter(this::checkData)
                        .map(Integer::valueOf)
                        .map(Platform::getByKey)
                        .collect(Collectors.toList());
    }

    private boolean checkData(String data) {
        return Objects.nonNull(data) &&
                !data.isEmpty() &&
                data.trim().chars().allMatch(Character::isDigit);
    }
}
