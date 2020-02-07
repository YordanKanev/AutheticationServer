package bg.sofia.uni.fmi.mjt.authentication.server.utils.adapters;

import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import com.google.gson.*;

import java.lang.reflect.Type;

public class UserInterfaceAdapter implements JsonSerializer<User>, JsonDeserializer<User> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    public User deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }
    public JsonElement serialize(User jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }
    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}
