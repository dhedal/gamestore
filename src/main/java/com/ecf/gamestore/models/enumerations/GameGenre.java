package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.GameGenreDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = GameGenreDeserializer.class)
public enum GameGenre {
    UNDEFINED(0, "Undefined", "Genre non défini."),
    PINBALL(1, "Pinball", "Jeux de flipper."),
    ADVENTURE(2, "Adventure", "Jeux d'aventure impliquant exploration et résolution d'énigmes."),
    INDIE(3, "Indie", "Jeux développés indépendamment."),
    ARCADE(4, "Arcade", "Jeux courts et rapides, souvent inspirés des machines d'arcade."),
    VISUAL_NOVEL(5, "Visual Novel", "Jeux interactifs basés sur une narration visuelle."),
    CARD_BOARD_GAME(6, "Card & Board Game", "Jeux de cartes et de société."),
    MOBA(7, "MOBA", "Multiplayer Online Battle Arena."),
    POINT_AND_CLICK(8, "Point-and-click", "Jeux d'aventure où l'on clique pour interagir."),
    FIGHTING(9, "Fighting", "Jeux de combat."),
    SHOOTER(10, "Shooter", "Jeux de tir."),
    MUSIC(11, "Music", "Jeux basés sur la musique et le rythme."),
    PLATFORM(12, "Platform", "Jeux de plateformes avec sauts et navigation."),
    PUZZLE(13, "Puzzle", "Jeux de casse-tête et résolution d'énigmes."),
    RACING(14, "Racing", "Jeux de course."),
    RTS(15, "Real Time Strategy (RTS)", "Jeux de stratégie en temps réel."),
    RPG(16, "Role-playing (RPG)", "Jeux de rôle avec développement de personnage."),
    SIMULATOR(17, "Simulator", "Jeux de simulation."),
    SPORT(18, "Sport", "Jeux de sports."),
    STRATEGY(19, "Strategy", "Jeux de stratégie."),
    TBS(20, "Turn-based strategy (TBS)", "Jeux de stratégie au tour par tour."),
    TACTICAL(21, "Tactical", "Jeux tactiques nécessitant de la stratégie."),
    HACK_AND_SLASH(22, "Hack and slash/Beat 'em up", "Jeux axés sur le combat contre des ennemis."),
    QUIZ_TRIVIA(23, "Quiz/Trivia", "Jeux de quiz et de trivia.");

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
                .findFirst().orElse(UNDEFINED);
    }
}