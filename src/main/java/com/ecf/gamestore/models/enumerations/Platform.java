package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.PlatformDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = PlatformDeserializer.class)
public enum Platform {
    UNDEFINED( 0, "indéfinie", ""),
    PC( 1, "pc", "assets/icons/icon-pc.svg"),
    PS_5(2, "playstation 5", "assets/icons/icon-playstation.svg"),
    SWITCH(3, "switch", "assets/icons/icon-nitendo.svg"),
    XBOX_SERIES(4, "xbox series", "assets/icons/icon-xbox.svg");

    private Integer key;
    private String label;
    private String icon;

    Platform(Integer key, String label, String icon) {
        this.key = key;
        this.label = label;
        this.icon = icon;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public static Platform getByKey(int key) {
        return Stream.of(Platform.values())
                .filter(platform -> platform.key.intValue() == key)
                .findFirst().orElse(UNDEFINED);
    }
}
