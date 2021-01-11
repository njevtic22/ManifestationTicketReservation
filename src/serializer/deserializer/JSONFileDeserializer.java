package serializer.deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Salesman;
import model.User;
import serializer.serializedModel.jsonModel.JSONAdmin;
import serializer.serializedModel.jsonModel.JSONSalesman;

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
    private final Map<Long, Salesman> salesmanRepository;


    private Map<Long, JSONAdmin> jsonAdmins;
    private Map<Long, JSONSalesman> jsonSalesmen;

    public JSONFileDeserializer(Gson gson, Map<String, String> filePaths, Map<Long, Admin> adminRepository, Map<Long, Salesman> salesmanRepository) {
        this.gson = gson;
        this.filePaths = filePaths;
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
    }

    public void loadData() {
        try {
            loadAdmins();
            loadSalesmen();
            User.initGenerator((long) adminRepository.size() + salesmanRepository.size());
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

//        User.initGenerator((long) adminRepository.size());
    }

    @Override
    public void loadSalesmen() throws FileNotFoundException {
        Type jsonSalesmanType = new TypeToken<HashMap<Long, JSONSalesman>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("salesmen"));
        jsonSalesmen = gson.fromJson(reader, jsonSalesmanType);

        jsonSalesmen.values().forEach(jsonSalesman -> {
            Salesman salesman = toModel(jsonSalesman);
            salesmanRepository.put(salesman.getId(), salesman);
        });

//        User.initGenerator((long) adminRepository.size() + salesmanRepository.size());
    }

    @Override
    public void buildReferences() {
        // TODO: build reference between salesman and manifestation
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

    private Salesman toModel(JSONSalesman jsonSalesman) {
        return new Salesman(
                jsonSalesman.id,
                jsonSalesman.name,
                jsonSalesman.surname,
                jsonSalesman.username,
                jsonSalesman.password,
                jsonSalesman.dateOfBirth,
                jsonSalesman.gender,
                jsonSalesman.archived
        );
    }
}
