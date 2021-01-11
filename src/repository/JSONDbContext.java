package repository;

import com.google.gson.Gson;
import model.Admin;
import model.Salesman;
import serializer.deserializer.JSONFileDeserializer;
import serializer.serializer.AdminJSONFileSerializer;
import serializer.serializer.FileSerializer;
import serializer.serializer.SalesmanJSONFileSerializer;

import java.util.HashMap;
import java.util.Map;

public class JSONDbContext {
    private AdminJSONRepository adminRepository;
    private SalesmanJSONRepository salesmanRepository;


    private FileSerializer<Admin, Long> adminFileSerializer;
    private FileSerializer<Salesman, Long> salesmanFileSerializer;


    private JSONFileDeserializer fileDeserializer;

    private Map<String, String> filePaths;

    public JSONDbContext(Gson gson, String directoryPath, String separator) {

        initFilePaths(directoryPath, separator);

        initSerializers();

        initRepositories();

        fileDeserializer = new JSONFileDeserializer(
                gson,
                filePaths,
                adminRepository.getData(),
                salesmanRepository.getData()
        );
        fileDeserializer.loadData();
    }

    private void initFilePaths(String directoryPath, String separator) {
        filePaths = new HashMap<>();
        filePaths.put("admins", directoryPath + separator + "admins.json");
        filePaths.put("salesmen", directoryPath + separator + "salesmen.json");
    }

    private void initSerializers() {
        adminFileSerializer = new AdminJSONFileSerializer(filePaths.get("admins"));
        salesmanFileSerializer = new SalesmanJSONFileSerializer(filePaths.get("salesmen"));
    }

    private void initRepositories() {
        adminRepository = new AdminJSONRepository(adminFileSerializer);
        salesmanRepository = new SalesmanJSONRepository(salesmanFileSerializer);
    }

    public UserRepository<Admin> getAdminRepository() {
        return adminRepository;
    }

    public UserRepository<Salesman> getSalesmanRepository() {
        return salesmanRepository;
    }
}
