package bg.sofia.uni.fmi.mjt.authentication.server.utils.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateTimeInterfaceAdapter implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ZonedDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString()).toLocalDateTime();
    }
}
