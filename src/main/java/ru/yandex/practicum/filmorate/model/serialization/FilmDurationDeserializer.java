package ru.yandex.practicum.filmorate.model.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class FilmDurationDeserializer extends StdDeserializer<Duration> {

    public FilmDurationDeserializer() {
        this(null);
    }

    public FilmDurationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return Duration.ofMinutes(Long.parseLong(jsonParser.getText()));
    }
}
