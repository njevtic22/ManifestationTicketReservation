package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Image;
import model.Location;
import model.Manifestation;
import model.Ticket;
import program.ProgramFactory;
import serializer.gsonSerializer.ImageSerializer;
import serializer.gsonSerializer.LocationSerializer;
import serializer.gsonSerializer.TicketListSerializer;
import serializer.gsonSerializer.WithdrawalHistoryListSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ManifestationJSONFileSerializer implements FileSerializer<Manifestation, Long> {
    private final String filePath;

    public ManifestationJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, Manifestation> data) {
        Type ticketsType = new TypeToken<List<Ticket>>() {}.getType();

        Gson gson = new GsonBuilder()
                .setDateFormat(ProgramFactory.DATE_FORMAT)
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(Location.class, new LocationSerializer())
                .registerTypeAdapter(Image.class, new ImageSerializer())
                .registerTypeAdapter(ticketsType, new TicketListSerializer())
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
