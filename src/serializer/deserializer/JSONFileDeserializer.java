package serializer.deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import serializer.serializedModel.jsonModel.JSONAdmin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSONFileDeserializer implements FileDeserializer {
    private final Gson gson;
    private final Map<String, String> filePaths;

    // Injected from adminRepository
    private final Map<Long, Admin> adminRepository;


    private Map<Long, JSONAdmin> jsonAdmins;

    public JSONFileDeserializer(Gson gson, Map<String, String> filePaths, Map<Long, Admin> adminRepository) {
        this.gson = gson;
        this.filePaths = filePaths;
        this.adminRepository = adminRepository;
    }

    public void loadData() {
        try {
            loadAdmins();
            buildReferences();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAdmins() throws FileNotFoundException {
        Type jsonAdminType = new TypeToken<HashMap<Long, JSONAdmin>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("admins"));
        jsonAdmins = gson.fromJson(reader, jsonAdminType);

        jsonAdmins.values().forEach(jsonAdmin -> {
            Admin admin = toModel(jsonAdmin);
            adminRepository.put(admin.getId(), admin);
        });

        Admin.initGenerator((long) adminRepository.size());
    }

    private Admin toModel(JSONAdmin jsonAdmin) {
        return new Admin(
                jsonAdmin.id,
                jsonAdmin.name,
                jsonAdmin.surname,
                jsonAdmin.username,
                jsonAdmin.password,
                jsonAdmin.dateOfBirth,
                jsonAdmin.gender,
                jsonAdmin.archived
        );
    }

    @Override
    public void buildReferences() {

    }
}
