package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Manifestation;
import model.Salesman;
import program.ProgramFactory;
import serializer.gsonSerializer.ManifestationListSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesmanJSONFileSerializer  implements FileSerializer<Salesman, Long> {
    private final String filePath;

    public SalesmanJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, Salesman> data) {
        Type manifestationsType = new TypeToken<List<Manifestation>>() {}.getType();

        Gson gson = new GsonBuilder()
                .setDateFormat(ProgramFactory.dateFormat)
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(manifestationsType, new ManifestationListSerializer())
                .create();

        try {
            FileWriter writer = new FileWriter(new File(filePath));
            writer.write(gson.toJson(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
