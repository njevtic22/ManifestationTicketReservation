package serializer.gsonSerializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Review;

import java.lang.reflect.Type;
import java.util.List;

public class ReviewListSerializer implements JsonSerializer<List<Review>> {
    @Override
    public JsonElement serialize(List<Review> reviews, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray reviewArray = new JsonArray();

        for (Review review : reviews)
            reviewArray.add(new JsonPrimitive(review.getId()));

        return reviewArray;
    }
}
