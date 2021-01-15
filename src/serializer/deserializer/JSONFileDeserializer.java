package serializer.deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Admin;
import model.Customer;
import model.CustomerType;
import model.Gender;
import model.Salesman;
import model.User;
import serializer.serializedModel.jsonModel.JSONAdmin;
import serializer.serializedModel.jsonModel.JSONCustomer;
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
    private final Map<Long, Customer> customerRepository;


    private Map<Long, JSONAdmin> jsonAdmins;
    private Map<Long, JSONSalesman> jsonSalesmen;
    private Map<Long, JSONCustomer> jsonCustomers;

    public JSONFileDeserializer(Gson gson, Map<String, String> filePaths, Map<Long, Admin> adminRepository, Map<Long, Salesman> salesmanRepository, Map<Long, Customer> customerRepository) {
        this.gson = gson;
        this.filePaths = filePaths;
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
    }

    public void loadData() {
        try {
            loadAdmins();
            loadSalesmen();
            loadCustomers();
            User.initGenerator(
                    (long)
                            adminRepository.size() +
                            salesmanRepository.size() +
                            customerRepository.size()
            );
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
    public void loadCustomers() throws FileNotFoundException {
        Type jsonCustomerType = new TypeToken<HashMap<Long, JSONCustomer>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("customers"));
        jsonCustomers = gson.fromJson(reader, jsonCustomerType);

        jsonCustomers.values().forEach(jsonCustomer -> {
            Customer customer = toModel(jsonCustomer);
            customerRepository.put(customer.getId(), customer);
        });

//        User.initGenerator((long) adminRepository.size() + salesmanRepository.size() + customerRepository.size());
    }

    @Override
    public void buildReferences() {
        // TODO: build reference between salesman and manifestation

        // TODO: build reference between customer and ticket
        // TODO: build reference between customer and history
    }

    private Admin toModel(JSONAdmin jsonAdmin) {
        return new Admin(
                jsonAdmin.id,
                jsonAdmin.name,
                jsonAdmin.surname,
                jsonAdmin.username,
                jsonAdmin.password,
                jsonAdmin.dateOfBirth,
                Gender.valueOf(jsonAdmin.gender),
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
                Gender.valueOf(jsonSalesman.gender),
                jsonSalesman.archived
        );
    }

    private Customer toModel(JSONCustomer jsonCustomer) {
        return new Customer(
                jsonCustomer.id,
                jsonCustomer.name,
                jsonCustomer.surname,
                jsonCustomer.username,
                jsonCustomer.password,
                jsonCustomer.dateOfBirth,
                Gender.valueOf(jsonCustomer.gender),

                jsonCustomer.archived,
                jsonCustomer.points,
                CustomerType.valueOf(jsonCustomer.type)
        );
    }
}
