package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.PegiDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = PegiDeserializer.class)
public enum Pegi {
    UNDEFINED(0, "indéfinie", "pegi indéfinie"),
    PEGI_3(1, "PEGI 3", "Convient à tous les âges. Le contenu ne doit pas inclure de sons ou d'images susceptibles de faire peur aux jeunes enfants. Une légère violence dans un contexte comique est acceptable."),
    PEGI_7(2, "PEGI 7", "Le contenu peut inclure des scènes ou des sons qui pourraient effrayer les jeunes enfants. Les formes légères de violence dans un cadre irréaliste ou de dessin animé sont acceptables."),
    PEGI_12(3, "PEGI 12", "Convient aux enfants de 12 ans et plus. Inclut une violence plus graphique envers des personnages fantastiques et une violence non graphique envers des personnages de type humain."),
    PEGI_16(4, "PEGI 16", "Convient aux adolescents de 16 ans et plus. Peut inclure des scènes de violence réaliste, une sexualité explicite et un langage fort."),
    PEGI_18(5, "PEGI 18", "Convient uniquement aux adultes. Contenu qui peut inclure une violence extrême, un langage vulgaire, une sexualité explicite, ou des références à l'usage de drogues.");


    private Integer key;
    private String label;
    private String description;
    Pegi(Integer key, String label, String description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public Integer getKey() { return this.key;}

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static Pegi getByKey(int key) {
        return Stream.of(Pegi.values())
                .filter(pegi -> pegi.key.intValue() == key)
                .findFirst().orElse(UNDEFINED);

    }

    public static List<Pegi> list() {
        return Stream.of(Pegi.values())
                .filter(pegi -> !Objects.equals(UNDEFINED, pegi))
                .collect(Collectors.toList());
    }
}
