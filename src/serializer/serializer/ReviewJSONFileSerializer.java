package serializer.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Customer;
import model.Manifestation;
import model.Review;
import program.ProgramFactory;
import serializer.gsonSerializer.CustomerSerializer;
import serializer.gsonSerializer.ManifestationSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReviewJSONFileSerializer implements FileSerializer<Review, Long> {
    private final String filePath;

    public ReviewJSONFileSerializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Map<Long, Review> data) {
        Gson gson = new GsonBuilder()
                .setDateFormat(ProgramFactory.DATE_FORMAT)
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(Customer.class, new CustomerSerializer())
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
