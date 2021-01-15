package serializer.gsonSerializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Ticket;

import java.lang.reflect.Type;
import java.util.List;

public class TicketListSerializer implements JsonSerializer<List<Ticket>> {
    @Override
    public JsonElement serialize(List<Ticket> tickets, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray ticketsArray = new JsonArray();

//        for (Ticket ticket : tickets)
//            ticketsArray.add(new JsonPrimitive(ticket.getId()));

        tickets.forEach(ticket -> ticketsArray.add(new JsonPrimitive(ticket.getId())));

        return ticketsArray;
    }
}
