package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Admin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class AdminJSONFileSerializer implements FileSerializer<Admin, Long> {
    private final String filePath;

    public AdminJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, Admin> data) {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd.MM.yyyy. HH:mm:ss")
                .setPrettyPrinting()
                .serializeNulls()
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
