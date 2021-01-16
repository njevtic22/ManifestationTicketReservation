package serializer.gsonSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Image;

import java.lang.reflect.Type;

public class ImageSerializer implements JsonSerializer<Image> {
    @Override
    public JsonElement serialize(Image image, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonImage = new JsonObject();
        jsonImage.addProperty("id", image.getId());
        jsonImage.addProperty("location", image.getLocation());

        return jsonImage;
    }
}
