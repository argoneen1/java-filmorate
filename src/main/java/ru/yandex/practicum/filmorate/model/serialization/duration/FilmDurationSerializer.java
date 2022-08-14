package ru.yandex.practicum.filmorate.model.serialization.duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

public class FilmDurationSerializer extends StdSerializer<Duration> {

    public FilmDurationSerializer() {
        this(null);
    }

    public FilmDurationSerializer(Class<Duration> t) {
        super(t);
    }

    @Override
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(duration.toMinutes());
    }
}
