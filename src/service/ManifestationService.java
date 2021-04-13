package service;

import exception.ManifestationNotFoundException;
import exception.PlaceAndDateTakenException;
import exception.SalesmanNotFoundException;
import model.*;
import repository.Repository;
import repository.UserRepository;
import useCase.manifestation.*;
import useCase.manifestation.command.AddManifestationCommand;
import useCase.manifestation.command.ApproveOrRejectCommand;
import useCase.manifestation.command.UpdateLocationCommand;
import useCase.manifestation.command.UpdateManifestationCommand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

public class ManifestationService implements
        AddManifestationUseCase,
        GetAllManifestationsUseCase,
        GetAllManifestationsForSalesmanUseCase,
        GetAllCreatedManifestationsUseCase,
        BelongsToSalesmanUseCase,
        GetByIdManifestationUseCase,
        UpdateManifestationUseCase,
        UpdateLocationUseCase,
        DeleteManifestationUseCase,
        SetManifestationsToInactiveUseCase,
        ApproveOrRejectManifestationUseCase {
    private final SimpleDateFormat formatter;
    private final Repository<Manifestation, Long> manifestationRepository;
    private final Repository<Ticket, Long> ticketRepository;
    private final UserRepository<Salesman> salesmanRepository;

    public ManifestationService(SimpleDateFormat formatter, Repository<Manifestation, Long> manifestationRepository, Repository<Ticket, Long> ticketRepository, UserRepository<Salesman> salesmanRepository) {
        this.formatter = formatter;
        this.manifestationRepository = manifestationRepository;
        this.ticketRepository = ticketRepository;
        this.salesmanRepository = salesmanRepository;
    }

    public String base64ToImageLocation(String imageBase64, String imageType, long manifestationId) {
        if (imageBase64.trim().isEmpty() || imageType.trim().isEmpty())
            return "";

        try {
            String base64Header = "data:image/" + imageType + ";base64,";
            String trueBase64Image = imageBase64.substring(base64Header.length());
            byte[] decodedImage = Base64.getDecoder().decode(trueBase64Image);
            String imageLocation = "images/manifestation/manifestation " + manifestationId + "." + imageType;

            OutputStream stream = new FileOutputStream(imageLocation);
            stream.write(decodedImage);
            stream.close();

            return imageLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    public void addManifestation(AddManifestationCommand command) throws ParseException {
        Manifestation manifestation = new Manifestation(
                command.name,
                command.maxNumberOfTickets,
                command.regularTicketPrice,
                formatter.parse(command.holdingDate),
                command.description,
                ManifestationStatus.valueOf(command.status),
                ManifestationType.valueOf(command.type),
                false,
                new Location(
                        command.longitude,
                        command.latitude,
                        new Address(
                                command.street,
                                command.number,
                                command.city,
                                command.postalCode
                        )
                ),
                new Image(
                        base64ToImageLocation(command.imageBase64, command.imageType, manifestationRepository.count() + 1)
                )
        );

        ensurePlaceAndDateIsValid(manifestation);

        Salesman salesman = salesmanRepository.findByIdAndArchivedFalse(command.salesmanId)
                .orElseThrow(() -> new SalesmanNotFoundException(command.salesmanId));
        salesman.addManifestation(manifestation);

        manifestationRepository.save(manifestation);
        salesmanRepository.save(salesman);
    }

    @Override
    public Collection<Manifestation> getAllManifestations(User user) {
        Collection<Manifestation> manifestations = manifestationRepository.findAllByArchivedFalse();

//        if (user == null) {
//            manifestations = manifestationRepository.findAllByArchivedFalse()
//                    .stream()
//                    .filter(manifestation -> {
//                        ManifestationStatus status = manifestation.getStatus();
//                        if (status == ManifestationStatus.CREATED || status == ManifestationStatus.REJECTED)
//                            return false;
//                        else
//                            return true;
//                    })
//                    .collect(Collectors.toList());
//
//        } else if (user instanceof Admin) {
//            manifestations = manifestationRepository.findAllByArchivedFalse();
//
//        } else {
//            manifestations = manifestationRepository.findAllByArchivedFalse()
//                    .stream()
//                    .filter(manifestation -> {
//                        ManifestationStatus status = manifestation.getStatus();
//                        if (status == ManifestationStatus.CREATED || status == ManifestationStatus.REJECTED)
//                            return false;
//                        else
//                            return true;
//                    })
//                    .collect(Collectors.toList());
//        }

        return manifestations;
    }

    @Override
    public Collection<Manifestation> getAllManifestationsForSalesman(Salesman salesman) {
        return salesman.getManifestations()
                .stream()
                .filter(manifestation -> !manifestation.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Manifestation> getCreatedManifestations() {
        return manifestationRepository.findAllByArchivedFalse()
                .stream()
                .filter(manifestation -> manifestation.getStatus() == ManifestationStatus.CREATED)
                .collect(Collectors.toList());
    }

    @Override
    public boolean belongsToSalesman(Long manifestationId, Salesman salesman) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(manifestationId));

        boolean found = false;
        for (Manifestation salesmanManifestation : salesman.getManifestations()) {
            if (manifestation.equals(salesmanManifestation)) {
                found = true;
                break;
            }
        }

        return found;
    }
    @Override
    public Manifestation getByIdManifestation(Long id) {
        return manifestationRepository.findByIdAndArchivedFalse(id).orElseThrow(() -> new ManifestationNotFoundException(id));
    }

    @Override
    public void updateManifestation(UpdateManifestationCommand command) throws ParseException {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new ManifestationNotFoundException(command.id));

        manifestation = manifestation.clone();

        manifestation.setName(command.name);

        manifestation.setRegularTicketPrice(command.regularTicketPrice);
        manifestation.getTickets().forEach(ticket -> {
            if (ticket.getStatus() == TicketStatus.FREE)
                ticket.setPrice(command.regularTicketPrice);
        });

        manifestation.setHoldingDate(formatter.parse(command.holdingDate));
        manifestation.setDescription(command.description);
        manifestation.setType(ManifestationType.valueOf(command.type));

        manifestation.getImage().setLocation(base64ToImageLocation(command.imageBase64, command.imageType, manifestation.getId()));

        ensurePlaceAndDateIsValid(manifestation);
        manifestationRepository.save(manifestation);
    }

    @Override
    public void updateLocation(UpdateLocationCommand command) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(command.manifestationId));

        manifestation = manifestation.clone();

        final Location location = manifestation.getLocation();
        location.setLongitude(command.longitude);
        location.setLatitude(command.latitude);

        final Address address = location.getAddress();
        address.setStreet(command.street);
        address.setNumber(command.number);
        address.setCity(command.city);
        address.setPostalCode(command.postalCode);

        ensurePlaceAndDateIsValid(manifestation);

        manifestationRepository.save(manifestation);
    }

    @Override
    public void deleteManifestation(Long id) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new ManifestationNotFoundException(id));

        manifestation.archive();

        manifestation.getTickets().forEach(ticket -> {
            ticket.archive();
            ticketRepository.save(ticket);
        });

        manifestationRepository.save(manifestation);
    }

    @Override
    public void setToInactive(Long id) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new ManifestationNotFoundException(id));

        if (manifestation.getStatus() == ManifestationStatus.ACTIVE) {
            manifestation.setStatus(ManifestationStatus.INACTIVE);
            manifestationRepository.save(manifestation);
        }
    }

    @Override
    public void approveOrReject(ApproveOrRejectCommand command) {
        Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new ManifestationNotFoundException(command.id));

        manifestation = manifestation.clone();

        manifestation.setStatus(ManifestationStatus.valueOf(command.newStatus));

        manifestationRepository.save(manifestation);
    }

    public void ensurePlaceAndDateIsValid(Manifestation manifestation) {
        Collection<Manifestation> allManifestations = manifestationRepository.findAllByArchivedFalse();
        allManifestations.forEach(manifestationToCheck -> {
            if (!manifestationToCheck.isArchived()) {
                if (!manifestationToCheck.getId().equals(manifestation.getId())) {
                    if (isSamePlaceAndDate(manifestation, manifestationToCheck)) {
                        Manifestation.initGenerator(manifestationRepository.count());
                        Location.initGenerator(manifestationRepository.count());
                        Address.initGenerator(manifestationRepository.count());
                        Image.initGenerator(manifestationRepository.count());
                        throw new PlaceAndDateTakenException();
                    }
                }
            }
        });
    }

    public boolean isSamePlaceAndDate(final Manifestation manifestation1, final Manifestation manifestation2) {
        final Date holdingDate1 = manifestation1.getHoldingDate();
        final Address address1 = manifestation1.getLocation().getAddress();
        final double longitude1 = manifestation1.getLocation().getLongitude();
        final double latitude1 = manifestation1.getLocation().getLatitude();

        final Date holdingDate2 = manifestation2.getHoldingDate();
        final Address address2 = manifestation2.getLocation().getAddress();
        final double longitude2 = manifestation2.getLocation().getLongitude();
        final double latitude2 = manifestation2.getLocation().getLatitude();

        // https://jsfiddle.net/kux63f5e/21/
        // Either address or coordinates are overlapping
        if (address1.equals(address2) || isOverlap(latitude1, longitude1, latitude2, longitude2, 0.0003))
            // Check if dates are overlapping
            return isOverlap(holdingDate1, holdingDate2, 2);
        return false;
    }

    private boolean isOverlap(double latitude1, double longitude1, double latitude2, double longitude2, double plusMinusOffset) {
        double latitude1Upper  = latitude1  + plusMinusOffset;
        double latitude1Lower  = latitude1  - plusMinusOffset;
        double longitude1Right = longitude1 + plusMinusOffset;
        double longitude1Left  = longitude1 - plusMinusOffset;

        double latitude2Upper  = latitude2  + plusMinusOffset;
        double latitude2Lower  = latitude2  - plusMinusOffset;
        double longitude2Right = longitude2 + plusMinusOffset;
        double longitude2Left  = longitude2 - plusMinusOffset;

        if (isOverlap(latitude1Lower, latitude1Upper, latitude2Lower, latitude2Upper)) {
            return isOverlap(longitude1Left, longitude1Right, longitude2Left, longitude2Right);
        }
        return false;
    }

    private boolean isOverlap(double lowerBound1, double upperBound1, double lowerBound2, double upperBound2) {
        double chosenLowerBound = 0;
        double chosenUpperBound = 0;

//        if (lowerBound1 < lowerBound2)
//            chosenLowerBound = lowerBound2;
//        else
//            chosenLowerBound = lowerBound1;
//
//        if (upperBound1 < upperBound2)
//            chosenUpperBound = upperBound1;
//        else
//            chosenUpperBound = upperBound2;

        chosenLowerBound = Math.max(lowerBound1, lowerBound2);
        chosenUpperBound = Math.min(upperBound1, upperBound2);

        return !(chosenLowerBound > chosenUpperBound);
    }

    private boolean isOverlap(final Date startDate1, final Date startDate2, int eventHoursDuration) {
        GregorianCalendar calendar = new GregorianCalendar();

        calendar.setTime(startDate1);
        calendar.add(Calendar.HOUR, eventHoursDuration);
        Date endDate1 = calendar.getTime();

        calendar.setTime(startDate2);
        calendar.add(Calendar.HOUR, eventHoursDuration);
        Date endDate2 = calendar.getTime();

        return isOverlap(startDate1, endDate1, startDate2, endDate2);
    }

    private boolean isOverlap(final Date beginDate1, final Date endDate1, final Date beginDate2, final Date endDate2) {
        Date chosenBeginDate;
        Date chosenEndDate;

        if (beginDate1.before(beginDate2))
            chosenBeginDate = beginDate2;
        else
            chosenBeginDate = beginDate1;

        if (endDate1.before(endDate2))
            chosenEndDate = endDate1;
        else
            chosenEndDate = endDate2;

        return !chosenBeginDate.after(chosenEndDate);
    }
}
