package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.PlatformDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = PlatformDeserializer.class)
public enum Platform {
    NONE( 1, "none"),
    PC( 2, "pc"),
    PS_5(3, "playstation 5"),
    SWITCH(4, "switch"),
    XBOX_SERIES(5, "xbox series");

    private Integer key;
    private String label;

    Platform(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public static Platform getByKey(int key) {
        return Stream.of(Platform.values())
                .filter(platform -> platform.key.intValue() == key)
                .findFirst().orElse(null);
    }
}
