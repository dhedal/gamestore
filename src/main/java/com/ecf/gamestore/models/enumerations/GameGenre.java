package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.GameGenreDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = GameGenreDeserializer.class)
public enum GameGenre {
    ACTION(1, "Action", "Jeux qui nécessitent de la rapidité et des réflexes."),
    ADVENTURE(2, "Adventure", "Jeux centrés sur la narration et la résolution de puzzles."),
    RPG(3, "RPG", "Jeux de rôle où le joueur incarne un personnage et suit une histoire."),
    SIMULATION(4, "Simulation", "Jeux qui imitent des activités réelles."),
    STRATEGY(5, "Strategy", "Jeux qui nécessitent une planification et une gestion de ressources."),
    SPORTS(6, "Sports", "Jeux basés sur des sports réels ou fictifs."),
    HORROR(7, "Horror", "Jeux conçus pour effrayer et créer une ambiance de suspense."),
    PUZZLE(8, "Puzzle", "Jeux basés sur la résolution d'énigmes.");

    private Integer key;
    private String label;
    private String description;

    GameGenre(Integer key, String label, String description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static GameGenre getByKey(int key){
        return Stream.of(GameGenre.values())
                .filter(gameGenre -> gameGenre.getKey().intValue() == key)
                .findFirst().orElse(null);
    }
}