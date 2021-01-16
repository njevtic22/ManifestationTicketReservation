package serializer.gsonSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Address;
import model.Location;

import java.lang.reflect.Type;

public class LocationSerializer implements JsonSerializer<Location> {
    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonLocation = new JsonObject();
        jsonLocation.addProperty("id", location.getId());
        jsonLocation.addProperty("longitude", location.getLongitude());
        jsonLocation.addProperty("latitude", location.getLatitude());

        Address address = location.getAddress();
        JsonObject jsonAddress = new JsonObject();
        jsonAddress.addProperty("id", address.getId());
        jsonAddress.addProperty("street", address.getStreet());
        jsonAddress.addProperty("number", address.getNumber());
        jsonAddress.addProperty("city", address.getCity());
        jsonAddress.addProperty("postalCode", address.getPostalCode());

        return jsonLocation;
    }
}
