package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.CommandStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = CommandStatusDeserializer.class)
public enum CommandStatus {
    PENDING(1, "en attente"),
    VALIDATED(2, "validé"),
    DELIVERED(3, "livré");


    private Integer key;
    private String label;

    CommandStatus(Integer key, String label){
        this.key = key;
        this.label = label;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public static CommandStatus getByKey(int key) {
        return Stream.of(CommandStatus.values())
                .filter(commandStatus -> commandStatus.key.intValue() == key)
                .findFirst().orElse(CommandStatus.PENDING);
    }
}
