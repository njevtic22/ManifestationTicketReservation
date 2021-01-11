package serializer.gsonSerializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Manifestation;

import java.lang.reflect.Type;
import java.util.List;

public class ManifestationListSerializer implements JsonSerializer<List<Manifestation>> {
    @Override
    public JsonElement serialize(List<Manifestation> manifestations, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray manifestationsArray = new JsonArray();

        for (Manifestation manifestation : manifestations)
            manifestationsArray.add(new JsonPrimitive(manifestation.getId()));

        return manifestationsArray;
    }
}
