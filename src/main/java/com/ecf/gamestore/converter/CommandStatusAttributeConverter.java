package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.CommandStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class CommandStatusAttributeConverter implements AttributeConverter<CommandStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CommandStatus commandStatus) {
        return Objects.isNull(commandStatus) ? CommandStatus.PENDING.getKey() : commandStatus.getKey();
    }

    @Override
    public CommandStatus convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? CommandStatus.PENDING : CommandStatus.getByKey(key.intValue());
    }
}
