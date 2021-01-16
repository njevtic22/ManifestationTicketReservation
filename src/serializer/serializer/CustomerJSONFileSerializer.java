package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Customer;
import model.Ticket;
import model.WithdrawalHistory;
import program.ProgramFactory;
import serializer.gsonSerializer.TicketListSerializer;
import serializer.gsonSerializer.WithdrawalHistoryListSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CustomerJSONFileSerializer implements FileSerializer<Customer, Long> {
    private final String filePath;

    public CustomerJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, Customer> data) {
        Type ticketsType = new TypeToken<List<Ticket>>() {}.getType();
        Type historyType = new TypeToken<List<WithdrawalHistory>>() {}.getType();

        Gson gson = new GsonBuilder()
                .setDateFormat(ProgramFactory.DATE_FORMAT)
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(ticketsType, new TicketListSerializer())
                .registerTypeAdapter(historyType, new WithdrawalHistoryListSerializer())
                .create();

        try {
            FileWriter writer = new FileWriter(new File(filePath));
            writer.write(gson.toJson(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
