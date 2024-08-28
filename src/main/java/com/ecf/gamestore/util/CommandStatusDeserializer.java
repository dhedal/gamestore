package com.ecf.gamestore.util;

import com.ecf.gamestore.models.enumerations.CommandStatus;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CommandStatusDeserializer extends JsonDeserializer<CommandStatus> {
    @Override
    public CommandStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int key = node.get("key").intValue();
        return CommandStatus.getByKey(key);
    }
}
