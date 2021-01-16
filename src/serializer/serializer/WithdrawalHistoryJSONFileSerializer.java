package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Manifestation;
import model.WithdrawalHistory;
import program.ProgramFactory;
import serializer.gsonSerializer.ManifestationSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WithdrawalHistoryJSONFileSerializer implements FileSerializer<WithdrawalHistory, Long> {
    private final String filePath;

    public WithdrawalHistoryJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, WithdrawalHistory> data) {
        Gson gson = new GsonBuilder()
                .setDateFormat(ProgramFactory.DATE_FORMAT)
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(Manifestation.class, new ManifestationSerializer())
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
