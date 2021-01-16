package serializer.gsonSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Manifestation;

import java.lang.reflect.Type;

public class ManifestationSerializer implements JsonSerializer<Manifestation> {
    @Override
    public JsonElement serialize(Manifestation manifestation, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(manifestation.getId());
    }
}
