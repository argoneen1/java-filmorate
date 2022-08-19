package ru.yandex.practicum.filmorate.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;

public class GenreDeserializerIdOnly  extends StdDeserializer<Film.Genre> {

    public GenreDeserializerIdOnly() {
        this(null);
    }

    public GenreDeserializerIdOnly(Class<?> vc) {
        super(vc);
    }

    @Override
    public Film.Genre deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return Film.Genre.fromNumber(node.get("id").asInt());
    }
}