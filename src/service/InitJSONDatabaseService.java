package service;

import com.github.javafaker.Faker;
import model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import serializer.serializer.FileSerializer;
import useCase.database.InitDatabaseUseCase;
import useCase.database.LocationIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InitJSONDatabaseService implements InitDatabaseUseCase {
    private final long ADMIN_NUM    = 10;
    private final long SALESMAN_NUM = 10;
    private final long CUSTOMER_NUM = 10;
    private final long USER_NUM     = ADMIN_NUM + SALESMAN_NUM + CUSTOMER_NUM;

    // Number of locations in csv file
    private final long MAN_NUM = 120;
    private final long MAN_PER_SALESMAN          = MAN_NUM / SALESMAN_NUM;
    private final long CREATED_MAN_PER_SALESMAN  = MAN_PER_SALESMAN / 4 - 1;
    private final long REJECTED_MAN_PER_SALESMAN = MAN_PER_SALESMAN / 4 - 1;
    private final long ACTIVE_MAN_PER_SALESMAN   = MAN_PER_SALESMAN / 4 + 1;
    private final long INACTIVE_MAN_PER_SALESMAN = MAN_PER_SALESMAN / 4 + 1;

    // Injected from repository class
    private final Map<Long, Admin> adminRepository;
    private final Map<Long, Salesman> salesmanRepository;
    private final Map<Long, Customer> customerRepository;
    private final Map<Long, Ticket> ticketRepository;
    private final Map<Long, Manifestation> manifestationRepository;
    private final Map<Long, WithdrawalHistory> withdrawalHistoryRepository;
    private final Map<Long, Review> reviewRepository;

    private final FileSerializer<Admin, Long> adminFileSerializer;
    private final FileSerializer<Salesman, Long> salesmanFileSerializer;
    private final FileSerializer<Customer, Long> customerFileSerializer;
    private final FileSerializer<Ticket, Long> ticketFileSerializer;
    private final FileSerializer<Manifestation, Long> manifestationFileSerializer;
    private final FileSerializer<WithdrawalHistory, Long> withdrawalHistoryFileSerializer;
    private final FileSerializer<Review, Long> reviewFileSerializer;

    private final String locationPath;
    private LocationIterator locationIterator;
    private final SimpleDateFormat formatter;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Faker faker = new Faker();

    public InitJSONDatabaseService(
            String locationPath,
            String dateFormat,

            Map<Long, Admin> adminRepository,
            Map<Long, Salesman> salesmanRepository,
            Map<Long, Customer> customerRepository,
            Map<Long, Ticket> ticketRepository,
            Map<Long, Manifestation> manifestationRepository,
            Map<Long, WithdrawalHistory> withdrawalHistoryRepository,
            Map<Long, Review> reviewRepository,

            FileSerializer<Admin, Long> adminFileSerializer,
            FileSerializer<Salesman, Long> salesmanFileSerializer,
            FileSerializer<Customer, Long> customerFileSerializer,
            FileSerializer<Ticket, Long> ticketFileSerializer,
            FileSerializer<Manifestation, Long> manifestationFileSerializer,
            FileSerializer<WithdrawalHistory, Long> withdrawalHistoryFileSerializer,
            FileSerializer<Review, Long> reviewFileSerializer
    ) {
        this.locationPath = locationPath;
        this.locationIterator = new LocationIterator(locationPath);
        this.formatter = new SimpleDateFormat(dateFormat);

        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.manifestationRepository = manifestationRepository;
        this.withdrawalHistoryRepository = withdrawalHistoryRepository;
        this.reviewRepository = reviewRepository;

        this.adminFileSerializer = adminFileSerializer;
        this.salesmanFileSerializer = salesmanFileSerializer;
        this.customerFileSerializer = customerFileSerializer;
        this.ticketFileSerializer = ticketFileSerializer;
        this.manifestationFileSerializer = manifestationFileSerializer;
        this.withdrawalHistoryFileSerializer = withdrawalHistoryFileSerializer;
        this.reviewFileSerializer = reviewFileSerializer;
    }

//        faker.dragonBall().character();
//        faker.backToTheFuture().quote();
//        faker.friends().character();
//        faker.harryPotter().quote();
//        faker.chuckNorris().fact();
//        faker.gameOfThrones().quote();

//        faker.hitchhikersGuideToTheGalaxy();
//        faker.hobbit();
//        faker.howIMetYourMother();
//        faker.lordOfTheRings();
//        faker.pokemon();
//        faker.shakespeare();
//        faker.starTrek();

    @Override
    public void initDatabase() {
        this.clearDatabase();

        String passwordEncrypt = "$2y$10$3Hdi/LGDnymCd7xLLXsMpegmNtnopfTfHf1h7dGlg20740IqG/8TS";
        String description = "Opis";
        {
            description = "Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor. Beatae dicta corporis.Earum et delectus.In velit nobis.Reprehenderit libero laboriosam.Voluptas nam sit sed vitae est aut.Occaecati facere voluptatem eos.Occaecati esse nihil.Mollitia ipsa iusto.Fuga officiis accusamus nesciunt repudiandae.Et cum quia beatae rerum qui.Fugiat sint voluptatum vitae temporibus id quo.Fugiat quos vel sed ex.Voluptatem assumenda doloremque totam.Corporis laudantium dolorum eveniet sint.Qui ipsum est cupiditate libero.Eligendi debitis odit.Aut blanditiis sunt quibusdam et.Culpa enim culpa.Repellendus dolores natus qui explicabo.Minus nostrum tempora maiores.Officia dolorem esse est occaecati laudantium non dolorem.Voluptatem dolores eaque repellat voluptates voluptas.Quasi aut ut repudiandae.Dolorem consectetur quidem nihil sequi.Magni laudantium suscipit impedit ipsa tempore et.Reprehenderit dolores dolor aut qui sit.Sint voluptas ratione odio.Et explicabo odio tempora et esse.Magni enim praesentium omnis deserunt sed earum.Officiis aperiam temporibus dolor.";
        }


        for (int i = 0; i < ADMIN_NUM; i++) {
            Admin admin = new Admin(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    "admin" + (i + 1),
                    passwordEncrypt,
                    generateDateOfBirth(),
                    faker.options().option(Gender.class),
                    false
            );
            adminRepository.put(admin.getId(), admin);
        }

        ArrayList<Long> salesmanIds = new ArrayList<>();
        for (int i = 0; i < SALESMAN_NUM; i++) {
            Salesman salesman = new Salesman(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    "salesman" + (i + 1),
                    passwordEncrypt,
                    generateDateOfBirth(),
                    faker.options().option(Gender.MALE, Gender.FEMALE),
                    false
            );
            salesmanIds.add(salesman.getId());
            salesmanRepository.put(salesman.getId(), salesman);
        }

        for (int i = 0; i < CUSTOMER_NUM; i++) {
            Customer customer = new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    "customer" + (i + 1),
                    passwordEncrypt,
                    generateDateOfBirth(),
                    faker.options().option(Gender.class),
                    false,
                    0D,
                    CustomerType.BRONZE
            );
            customerRepository.put(customer.getId(), customer);
        }

        HashMap<Long, Manifestation> createdManifestations = new HashMap<>();
        HashMap<Long, Manifestation> rejectedManifestations = new HashMap<>();
        HashMap<Long, Manifestation> activeManifestations = new HashMap<>();
        HashMap<Long, Manifestation> inactiveManifestations = new HashMap<>();

        ArrayList<Long> salesmanIdsToClear = new ArrayList<>(salesmanIds);
        while (!salesmanIdsToClear.isEmpty()) {
            int indexToRemove = faker.number().numberBetween(0, salesmanIdsToClear.size());
            Long salesmanId = salesmanIdsToClear.remove(indexToRemove);
            Salesman salesman = salesmanRepository.get(salesmanId);

            for (int i = 0; i < CREATED_MAN_PER_SALESMAN; i++) {
                Manifestation createdManifestation = new Manifestation(
                        "", // generateManName(i + 1),
                        0,
                        generateManPrice(),
                        generateHoldingDateForCreatedMan(),
                        description,
                        ManifestationStatus.CREATED,
                        faker.options().option(ManifestationType.class),
                        false,
                        locationIterator.next(),
                        new Image(
                                ""
                        )
                );
                // Fix
                createdManifestation.setName(generateManName(createdManifestation.getId()));
                createdManifestation.getImage().setLocation("images/manifestation/manifestation " + createdManifestation.getId() + ".jpg");

                salesman.addManifestation(createdManifestation);
                createdManifestations.put(createdManifestation.getId(), createdManifestation);
                manifestationRepository.put(createdManifestation.getId(), createdManifestation);
            }

            for (int i = 0; i < REJECTED_MAN_PER_SALESMAN; i++) {
                Manifestation rejectedManifestation = new Manifestation(
                        "", // generateManName(i + 1),
                        0,
                        generateManPrice(),
                        generateHoldingDateForRejectedMan(),
                        description,
                        ManifestationStatus.REJECTED,
                        faker.options().option(ManifestationType.class),
                        false,
                        locationIterator.next(),
                        new Image(
                                ""
                        )
                );
                // Fix
                rejectedManifestation.setName(generateManName(rejectedManifestation.getId()));
                rejectedManifestation.getImage().setLocation("images/manifestation/manifestation " + rejectedManifestation.getId() + ".jpg");

                salesman.addManifestation(rejectedManifestation);
                rejectedManifestations.put(rejectedManifestation.getId(), rejectedManifestation);
                manifestationRepository.put(rejectedManifestation.getId(), rejectedManifestation);
            }

            for (int i = 0; i < ACTIVE_MAN_PER_SALESMAN; i++) {
                Manifestation activeManifestation = new Manifestation(
                        "", // generateManName(i + 1),
                        0,
                        generateManPrice(),
                        generateHoldingDateForActiveMan(),
                        description,
                        ManifestationStatus.ACTIVE,
                        faker.options().option(ManifestationType.class),
                        false,
                        locationIterator.next(),
                        new Image(
                                ""
                        )
                );
                // Fix
                activeManifestation.setName(generateManName(activeManifestation.getId()));
                activeManifestation.getImage().setLocation("images/manifestation/manifestation " + activeManifestation.getId() + ".jpg");

                salesman.addManifestation(activeManifestation);
                activeManifestations.put(activeManifestation.getId(), activeManifestation);
                manifestationRepository.put(activeManifestation.getId(), activeManifestation);
            }

            for (int i = 0; i < INACTIVE_MAN_PER_SALESMAN; i++) {
                Manifestation inactiveManifestation = new Manifestation(
                        "", // generateManName(i + 1),
                        0,
                        generateManPrice(),
                        generateHoldingDateForInactiveMan(),
                        description,
                        ManifestationStatus.INACTIVE,
                        faker.options().option(ManifestationType.class),
                        false,
                        locationIterator.next(),
                        new Image(
                                ""
                        )
                );
                // Fix
                inactiveManifestation.setName(generateManName(inactiveManifestation.getId()));
                inactiveManifestation.getImage().setLocation("images/manifestation/manifestation " + inactiveManifestation.getId() + ".jpg");

                salesman.addManifestation(inactiveManifestation);
                inactiveManifestations.put(inactiveManifestation.getId(), inactiveManifestation);
                manifestationRepository.put(inactiveManifestation.getId(), inactiveManifestation);
            }
        }



        adminFileSerializer.save(adminRepository);
        salesmanFileSerializer.save(salesmanRepository);
        customerFileSerializer.save(customerRepository);
        ticketFileSerializer.save(ticketRepository);
        manifestationFileSerializer.save(manifestationRepository);
        withdrawalHistoryFileSerializer.save(withdrawalHistoryRepository);
        reviewFileSerializer.save(reviewRepository);
    }

    private String generateManName(long manId) {
        ManifestationType manType = faker.options().option(ManifestationType.class);
        return toCapitalCase(manType.toString()) + " " + (manId) + ": " + faker.friends().character();
    }
    private String toCapitalCase(String name) {
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    private double generateManPrice() {
        int number = faker.number().numberBetween(40, 90);
        return number * 100;
    }

    private Date generateHoldingDateForCreatedMan() {
        String timeOfBirth = "2022. 08:00:00";
        String dateStr = generateDay() + "." + generateMonth() + "." + timeOfBirth;
        return parseDate(dateStr);
    }

    private Date generateHoldingDateForRejectedMan() {
        String timeOfBirth = "2023. 11:00:00";
        String dateStr = generateDay() + "." + generateMonth() + "." + timeOfBirth;
        return parseDate(dateStr);
    }

    private Date generateHoldingDateForActiveMan() {
        String timeOfBirth = "2021. 14:00:00";
        // Last changed 14.4.2021.
        int futureMonth = faker.number().numberBetween(6, 8);
        String dateStr = generateDay() + "." + futureMonth + "." + timeOfBirth;
        return parseDate(dateStr);
    }

    private Date generateHoldingDateForInactiveMan() {
        String timeOfBirth = "2020. 17:00:00";
        String dateStr = generateDay() + "." + generateMonth() + "." + timeOfBirth;
        return parseDate(dateStr);
    }

    private Date generateDateOfBirth() {
        String timeOfBirth = "2000. 08:00:00";
        String dateStr = generateDay() + "." + generateMonth() + "." + timeOfBirth;
        return parseDate(dateStr);
    }

    private Date parseDate(String dateStr) {
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private int generateDay() {
        return faker.number().numberBetween(1, 28);
    }

    private int generateMonth() {
        return faker.number().numberBetween(1, 12);
    }

    private void clearDatabase() {
        this.locationIterator = new LocationIterator(locationPath);

        this.adminRepository.clear();
        this.salesmanRepository.clear();
        this.customerRepository.clear();
        this.ticketRepository.clear();
        this.manifestationRepository.clear();
        this.withdrawalHistoryRepository.clear();
        this.reviewRepository.clear();

        User.initGenerator(0L);
        Ticket.initGenerator(0L);
        Manifestation.initGenerator(0L);
        Location.initGenerator(0L);
        Address.initGenerator(0L);
        Image.initGenerator(0L);
        WithdrawalHistory.initGenerator(0L);
        Review.initGenerator(0L);
    }
}
