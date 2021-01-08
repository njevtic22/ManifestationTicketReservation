package repository;

import com.google.gson.Gson;
import model.Admin;
import serializer.deserializer.JSONFileDeserializer;
import serializer.serializer.AdminJSONFileSerializer;
import serializer.serializer.FileSerializer;

import java.util.HashMap;
import java.util.Map;

public class JSONDbContext {
    private AdminJSONRepository adminRepository;


    private FileSerializer<Admin, Long> adminFileSerializer;


    private JSONFileDeserializer fileDeserializer;

    private Map<String, String> filePaths;

    public JSONDbContext(Gson gson, String directoryPath, String separator) {

        initFilePaths(directoryPath, separator);

        initSerializers();

        initRepositories();

        fileDeserializer = new JSONFileDeserializer(
                gson,
                filePaths,
                adminRepository.getData()
        );
        fileDeserializer.loadData();
    }

    private void initFilePaths(String directoryPath, String separator) {
        filePaths = new HashMap<>();
        filePaths.put("admins", directoryPath + separator + "admins.json");
    }

    private void initSerializers() {
        adminFileSerializer = new AdminJSONFileSerializer(filePaths.get("admins"));
    }

    private void initRepositories() {
        adminRepository = new AdminJSONRepository(adminFileSerializer);
    }

    public Repository<Admin,Long> getAdminRepository() {
        return adminRepository;
    }
}
