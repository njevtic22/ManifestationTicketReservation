package serializer.gsonSerializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.WithdrawalHistory;

import java.lang.reflect.Type;
import java.util.List;

public class WithdrawalHistoryListSerializer implements JsonSerializer<List<WithdrawalHistory>> {
    @Override
    public JsonElement serialize(List<WithdrawalHistory> withdrawalHistories, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray historyArray = new JsonArray();

//        for (WithdrawalHistory withdrawalHistory : withdrawalHistories)
//            historyArray.add(new JsonPrimitive(withdrawalHistory.getId()));

        withdrawalHistories.forEach(history ->
                historyArray.add(new JsonPrimitive(history.getId()))
        );

        return historyArray;
    }
}
