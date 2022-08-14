package ru.yandex.practicum.filmorate.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;

public class MpaDeserializer extends StdDeserializer<Film.MPARating> {

    public MpaDeserializer() {
        this(null);
    }

    public MpaDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Film.MPARating deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        return Film.MPARating.fromNumber(node.get("id").asInt());

    }
}
