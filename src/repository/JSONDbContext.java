package repository;

import com.google.gson.Gson;
import model.Admin;
import model.Customer;
import model.Salesman;
import serializer.deserializer.JSONFileDeserializer;
import serializer.serializer.AdminJSONFileSerializer;
import serializer.serializer.CustomerFileSerializer;
import serializer.serializer.FileSerializer;
import serializer.serializer.SalesmanJSONFileSerializer;

import java.util.HashMap;
import java.util.Map;

public class JSONDbContext {
    private AdminJSONRepository adminRepository;
    private SalesmanJSONRepository salesmanRepository;
    private CustomerJSONRepository customerRepository;
    private AuthenticationJSONRepository authenticationRepository;

    private FileSerializer<Admin, Long> adminFileSerializer;
    private FileSerializer<Salesman, Long> salesmanFileSerializer;
    private FileSerializer<Customer, Long> customerFileSerializer;


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
                salesmanRepository.getData(),
                customerRepository.getData()
        );
        fileDeserializer.loadData();
    }

    private void initFilePaths(String directoryPath, String separator) {
        filePaths = new HashMap<>();
        filePaths.put("admins", directoryPath + separator + "admins.json");
        filePaths.put("salesmen", directoryPath + separator + "salesmen.json");
        filePaths.put("customers", directoryPath + separator + "customers.json");
    }

    private void initSerializers() {
        adminFileSerializer = new AdminJSONFileSerializer(filePaths.get("admins"));
        salesmanFileSerializer = new SalesmanJSONFileSerializer(filePaths.get("salesmen"));
        customerFileSerializer = new CustomerFileSerializer(filePaths.get("customers"));
    }

    private void initRepositories() {
        adminRepository = new AdminJSONRepository(adminFileSerializer);
        salesmanRepository = new SalesmanJSONRepository(salesmanFileSerializer);
        customerRepository = new CustomerJSONRepository(customerFileSerializer);
        authenticationRepository = new AuthenticationJSONRepository(
                adminRepository,
                salesmanRepository,
                customerRepository
        );
    }

    public UserRepository<Admin> getAdminRepository() {
        return adminRepository;
    }

    public UserRepository<Salesman> getSalesmanRepository() {
        return salesmanRepository;
    }

    public UserRepository<Customer> getCustomerRepository() {
        return customerRepository;
    }

    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }
}
