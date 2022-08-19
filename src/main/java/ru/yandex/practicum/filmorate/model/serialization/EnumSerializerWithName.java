package ru.yandex.practicum.filmorate.model.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.yandex.practicum.filmorate.model.StoredEnum;

import java.io.IOException;

public class EnumSerializerWithName<T   extends Enum<T> & StoredEnum >  extends StdSerializer<T> {

    public EnumSerializerWithName() {
        this(null);
    }

    public EnumSerializerWithName(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T elem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("id");
        jsonGenerator.writeNumber(elem.getId());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeNumber("\"" + elem.getLocalizedName() + "\"");
        jsonGenerator.writeEndObject();
    }
}
