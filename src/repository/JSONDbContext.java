package repository;

import com.google.gson.Gson;
import model.Admin;
import model.Customer;
import model.Manifestation;
import model.Review;
import model.Salesman;
import model.Ticket;
import model.WithdrawalHistory;
import program.ProgramFactory;
import serializer.deserializer.JSONFileDeserializer;
import serializer.serializer.AdminJSONFileSerializer;
import serializer.serializer.CustomerJSONFileSerializer;
import serializer.serializer.FileSerializer;
import serializer.serializer.ManifestationJSONFileSerializer;
import serializer.serializer.ReviewJSONFileSerializer;
import serializer.serializer.SalesmanJSONFileSerializer;
import serializer.serializer.TicketJSONFileSerializer;
import serializer.serializer.WithdrawalHistoryJSONFileSerializer;
import service.InitJSONDatabaseService;
import useCase.database.InitDatabaseUseCase;

import java.util.HashMap;
import java.util.Map;

public class JSONDbContext {
    private AdminJSONRepository adminRepository;
    private SalesmanJSONRepository salesmanRepository;
    private CustomerJSONRepository customerRepository;
    private AuthenticationJSONRepository authenticationRepository;
    private TicketJSONRepository ticketRepository;
    private ManifestationJSONRepository manifestationRepository;
    private WithdrawalHistoryJSONRepository withdrawalHistoryRepository;
    private ReviewJSONRepository reviewRepository;

    private FileSerializer<Admin, Long> adminFileSerializer;
    private FileSerializer<Salesman, Long> salesmanFileSerializer;
    private FileSerializer<Customer, Long> customerFileSerializer;
    private FileSerializer<Ticket, Long> ticketFileSerializer;
    private FileSerializer<Manifestation, Long> manifestationFileSerializer;
    private FileSerializer<WithdrawalHistory, Long> withdrawalHistoryFileSerializer;
    private FileSerializer<Review, Long> reviewFileSerializer;

    private String directoryPath;
    private String separator;

    private JSONFileDeserializer fileDeserializer;

    private Map<String, String> filePaths;

    public JSONDbContext(Gson gson, String directoryPath, String separator) {
        this.directoryPath = directoryPath;
        this.separator = separator;

        initFilePaths(directoryPath, separator);

        initSerializers();

        initRepositories();

        fileDeserializer = new JSONFileDeserializer(
                gson,
                filePaths,
                adminRepository.getData(),
                salesmanRepository.getData(),
                customerRepository.getData(),
                ticketRepository.getData(),
                manifestationRepository.getData(),
                withdrawalHistoryRepository.getData(),
                reviewRepository.getData()
        );
        fileDeserializer.loadData();
    }

    private void initFilePaths(String directoryPath, String separator) {
        filePaths = new HashMap<>();
        filePaths.put("admins", directoryPath + separator + "admins.json");
        filePaths.put("salesmen", directoryPath + separator + "salesmen.json");
        filePaths.put("customers", directoryPath + separator + "customers.json");
        filePaths.put("tickets", directoryPath + separator + "tickets.json");
        filePaths.put("manifestations", directoryPath + separator + "manifestations.json");
        filePaths.put("histories", directoryPath + separator + "withdrawalHistory.json");
        filePaths.put("reviews", directoryPath + separator + "reviews.json");
    }

    private void initSerializers() {
        adminFileSerializer = new AdminJSONFileSerializer(filePaths.get("admins"));
        salesmanFileSerializer = new SalesmanJSONFileSerializer(filePaths.get("salesmen"));
        customerFileSerializer = new CustomerJSONFileSerializer(filePaths.get("customers"));
        ticketFileSerializer = new TicketJSONFileSerializer(filePaths.get("tickets"));
        manifestationFileSerializer = new ManifestationJSONFileSerializer(filePaths.get("manifestations"));
        withdrawalHistoryFileSerializer = new WithdrawalHistoryJSONFileSerializer(filePaths.get("histories"));
        reviewFileSerializer = new ReviewJSONFileSerializer(filePaths.get("reviews"));
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
        ticketRepository = new TicketJSONRepository(ticketFileSerializer);
        manifestationRepository = new ManifestationJSONRepository(manifestationFileSerializer);
        withdrawalHistoryRepository = new WithdrawalHistoryJSONRepository(withdrawalHistoryFileSerializer);
        reviewRepository = new ReviewJSONRepository(reviewFileSerializer);
    }

    public InitDatabaseUseCase getInitDatabaseService() {
//        return new InitJSONDatabaseService(
//                this.directoryPath + this.separator + "Locations.csv",
//                ProgramFactory.DATE_FORMAT,
//
//                adminRepository.getData(),
//                salesmanRepository.getData(),
//                customerRepository.getData(),
//                ticketRepository.getData(),
//                manifestationRepository.getData(),
//                withdrawalHistoryRepository.getData(),
//                reviewRepository.getData(),
//
//                adminFileSerializer,
//                salesmanFileSerializer,
//                customerFileSerializer,
//                ticketFileSerializer,
//                manifestationFileSerializer,
//                withdrawalHistoryFileSerializer,
//                reviewFileSerializer
//        );
        return null;
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

    public Repository<Ticket, Long> getTicketRepository() {
        return ticketRepository;
    }

    public Repository<Manifestation, Long> getManifestationRepository() {
        return manifestationRepository;
    }

    public Repository<WithdrawalHistory, Long> getHistoryRepository() {
        return withdrawalHistoryRepository;
    }

    public Repository<Review, Long> getReviewRepository() {
        return reviewRepository;
    }
}
