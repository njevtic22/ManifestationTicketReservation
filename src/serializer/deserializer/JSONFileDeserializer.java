package serializer.deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import serializer.serializedModel.jsonModel.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSONFileDeserializer implements FileDeserializer {
    private final Gson gson;
    private final Map<String, String> filePaths;

    // Injected from repository class
    private final Map<Long, Admin> adminRepository;
    private final Map<Long, Salesman> salesmanRepository;
    private final Map<Long, Customer> customerRepository;
    private final Map<Long, Ticket> ticketRepository;
    private final Map<Long, Manifestation> manifestationRepository;
    private final Map<Long, WithdrawalHistory> withdrawalHistoryRepository;
    private final Map<Long, Review> reviewRepository;


    private Map<Long, JSONAdmin> jsonAdmins;
    private Map<Long, JSONSalesman> jsonSalesmen;
    private Map<Long, JSONCustomer> jsonCustomers;
    private Map<Long, JSONTicket> jsonTickets;
    private Map<Long, JSONManifestation> jsonManifestations;
    private Map<Long, JSONHistory> jsonHistories;
    private Map<Long, JSONReview> jsonReviews;

    public JSONFileDeserializer(Gson gson, Map<String, String> filePaths, Map<Long, Admin> adminRepository, Map<Long, Salesman> salesmanRepository, Map<Long, Customer> customerRepository, Map<Long, Ticket> ticketRepository, Map<Long, Manifestation> manifestationRepository, Map<Long, WithdrawalHistory> withdrawalHistoryRepository, Map<Long, Review> reviewRepository) {
        this.gson = gson;
        this.filePaths = filePaths;
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.manifestationRepository = manifestationRepository;
        this.withdrawalHistoryRepository = withdrawalHistoryRepository;
        this.reviewRepository = reviewRepository;
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

            loadTickets();
            loadManifestations();
            loadWithdrawalHistory();
            loadReviews();
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
    public void loadTickets() throws FileNotFoundException {
        Type jsonTicketType = new TypeToken<HashMap<Long, JSONTicket>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("tickets"));
        jsonTickets = gson.fromJson(reader, jsonTicketType);

        jsonTickets.values().forEach(jsonTicket -> {
            Ticket ticket = toModel(jsonTicket);
            ticketRepository.put(ticket.getId(), ticket);
        });

        Ticket.initGenerator((long) ticketRepository.size());
    }

    @Override
    public void loadManifestations() throws FileNotFoundException {
        Type jsonManifestationType = new TypeToken<HashMap<Long, JSONManifestation>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("manifestations"));
        jsonManifestations = gson.fromJson(reader, jsonManifestationType);

        jsonManifestations.values().forEach(jsonManifestation -> {
            Manifestation manifestation = toModel(jsonManifestation);
            manifestationRepository.put(manifestation.getId(), manifestation);
        });

        long initCount = manifestationRepository.size();
        Manifestation.initGenerator(initCount);
        Location.initGenerator(initCount);
        Address.initGenerator(initCount);
        Image.initGenerator(initCount);
    }

    @Override
    public void loadWithdrawalHistory() throws FileNotFoundException {
        Type jsonHistoryType = new TypeToken<HashMap<Long, JSONHistory>>() {}.getType();
        FileReader reader = new FileReader(filePaths.get("histories"));
        jsonHistories = gson.fromJson(reader, jsonHistoryType);

        jsonHistories.values().forEach(jsonHistory -> {
            WithdrawalHistory history = toModel(jsonHistory);
            withdrawalHistoryRepository.put(history.getId(), history);
        });

        WithdrawalHistory.initGenerator((long) withdrawalHistoryRepository.size());
    }

    @Override
    public void loadReviews() throws FileNotFoundException {
        Type jsonReviewType = new TypeToken<HashMap<Long, JSONReview>>(){}.getType();
        FileReader reader = new FileReader(filePaths.get("reviews"));
        jsonReviews = gson.fromJson(reader, jsonReviewType);

        jsonReviews.values().forEach(jsonReview -> {
            Review review = toModel(jsonReview);
            reviewRepository.put(review.getId(), review);
        });

        Review.initGenerator((long) reviewRepository.size());
    }

    @Override
    public void buildReferences() {
        // building reference between salesman and manifestation
        jsonSalesmen.values().forEach(jsonSalesman -> {
            Salesman salesman = salesmanRepository.get(jsonSalesman.id);

            jsonSalesman.manifestations.forEach(manifestationId -> {
                Manifestation manifestation = manifestationRepository.get(manifestationId);
                salesman.addManifestation(manifestation);
            });
        });

        // building reference between customer and ticket and history
        jsonCustomers.values().forEach(jsonCustomer -> {
            Customer customer = customerRepository.get(jsonCustomer.id);

            jsonCustomer.tickets.forEach(ticketId -> {
                Ticket ticket = ticketRepository.get(ticketId);
                customer.addTicket(ticket);
                ticket.setCustomer(customer);
            });
            jsonCustomer.withdrawalHistory.forEach(historyId -> {
                WithdrawalHistory history = withdrawalHistoryRepository.get(historyId);
                customer.addHistory(history);
            });
        });

        // building reference between manifestation and ticket
        jsonManifestations.values().forEach(jsonManifestation -> {
            Manifestation manifestation = manifestationRepository.get(jsonManifestation.id);

            jsonManifestation.tickets.forEach(ticketId -> {
                Ticket ticket = ticketRepository.get(ticketId);

                // Fixing ticket price
                ticket.setPrice(manifestation.getRegularTicketPrice());

                manifestation.addTicket(ticket);
                ticket.setManifestation(manifestation);
            });
        });

        // building reference between history and manifestation
        jsonHistories.values().forEach(jsonHistory -> {
            WithdrawalHistory history = withdrawalHistoryRepository.get(jsonHistory.id);
            Manifestation manifestation = manifestationRepository.get(jsonHistory.manifestationId);

            history.setManifestation(manifestation);
        });

        // building references between review and customer and manifestation
        jsonReviews.values().forEach(jsonReview -> {
            Review review = reviewRepository.get(jsonReview.id);
            Customer customer = customerRepository.get(jsonReview.author);
            Manifestation manifestation = manifestationRepository.get(jsonReview.manifestation);

            review.setAuthor(customer);
            review.setManifestation(manifestation);
            manifestation.addReview(review);
        });
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

    private WithdrawalHistory toModel(JSONHistory jsonHistory) {
        return new WithdrawalHistory(
                jsonHistory.id,
                jsonHistory.withdrawalDate,
                jsonHistory.ticketId,
                jsonHistory.price,
                TicketType.valueOf(jsonHistory.type),
                jsonHistory.archived,
                null
//                manifestationRepository.get(jsonHistory.manifestationId)
        );
    }

    private Manifestation toModel(JSONManifestation jsonManifestation) {
        return new Manifestation(
                jsonManifestation.id,
                jsonManifestation.name,
                jsonManifestation.maxNumberOfTickets,
                jsonManifestation.regularTicketPrice,
                jsonManifestation.holdingDate,
                jsonManifestation.description,
                ManifestationStatus.valueOf(jsonManifestation.status),
                ManifestationType.valueOf(jsonManifestation.type),
                jsonManifestation.archived,

                toModel(jsonManifestation.location),
                toModel(jsonManifestation.image)

//                jsonManifestation.tickets
//                        .stream()
//                        .map(ticketRepository::get)
//                        .collect(Collectors.toList())

        );
    }

    private Location toModel(JSONLocation jsonLocation) {
        return new Location(
                jsonLocation.id,
                jsonLocation.longitude,
                jsonLocation.latitude,

                toModel(jsonLocation.address)
        );
    }

    private Address toModel(JSONAddress jsonAddress) {
        return new Address(
                jsonAddress.id,
                jsonAddress.street,
                jsonAddress.number,
                jsonAddress.city,
                jsonAddress.postalCode
        );
    }

    private Image toModel(JSONImage jsonImage) {
        return new Image(
                jsonImage.id,
                jsonImage.location
        );
    }

    private Ticket toModel(JSONTicket jsonTicket) {
        return new Ticket(
                jsonTicket.id,
                jsonTicket.appId,
                jsonTicket.price,
                TicketStatus.valueOf(jsonTicket.status),
                TicketType.valueOf(jsonTicket.type),
                jsonTicket.archived,
                null,
                null
        );
    }

    private Review toModel(JSONReview jsonReview) {
        return new Review(
                jsonReview.id,
                jsonReview.comment,
                jsonReview.rating,
                ReviewStatus.valueOf(jsonReview.status),
                jsonReview.archived,

                null,
                null
//                jsonReview.author,
//                jsonReview.manifestation
        );
    }
}
