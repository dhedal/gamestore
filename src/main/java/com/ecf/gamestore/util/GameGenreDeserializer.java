package com.ecf.gamestore.util;

import com.ecf.gamestore.models.enumerations.GameGenre;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class GameGenreDeserializer extends JsonDeserializer<GameGenre> {
    @Override
    public GameGenre deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int key = node.get("key").intValue();
        return GameGenre.getByKey(key);
    }
}