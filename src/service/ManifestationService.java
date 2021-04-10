package service;

import exception.ManifestationNotFoundException;
import exception.PlaceAndDateTakenException;
import exception.SalesmanNotFoundException;
import model.*;
import repository.Repository;
import repository.UserRepository;
import useCase.manifestation.AddManifestationUseCase;
import useCase.manifestation.DeleteManifestationUseCase;
import useCase.manifestation.GetAllCreatedManifestationsUseCase;
import useCase.manifestation.GetAllManifestationsForSalesmanUseCase;
import useCase.manifestation.GetAllManifestationsUseCase;
import useCase.manifestation.GetByIdManifestationUseCase;
import useCase.manifestation.UpdateLocationUseCase;
import useCase.manifestation.UpdateManifestationUseCase;
import useCase.manifestation.command.AddManifestationCommand;
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
        GetByIdManifestationUseCase,
        UpdateManifestationUseCase,
        UpdateLocationUseCase,
        DeleteManifestationUseCase {
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
        Collection<Manifestation> manifestations;

        if (user == null) {
            manifestations = manifestationRepository.findAllByArchivedFalse()
                    .stream()
                    .filter(manifestation -> {
                        ManifestationStatus status = manifestation.getStatus();
                        if (status == ManifestationStatus.CREATED || status == ManifestationStatus.REJECTED)
                            return false;
                        else
                            return true;
                    })
                    .collect(Collectors.toList());

        } else if (user instanceof Admin) {
            manifestations = manifestationRepository.findAllByArchivedFalse();

        } else {
            manifestations = manifestationRepository.findAllByArchivedFalse()
                    .stream()
                    .filter(manifestation -> {
                        ManifestationStatus status = manifestation.getStatus();
                        if (status == ManifestationStatus.CREATED || status == ManifestationStatus.REJECTED)
                            return false;
                        else
                            return true;
                    })
                    .collect(Collectors.toList());
        }

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
    public Manifestation getByIdManifestation(Long id) {
        return manifestationRepository.findByIdAndArchivedFalse(id).orElseThrow(() -> new ManifestationNotFoundException(id));
    }

    @Override
    public void updateManifestation(UpdateManifestationCommand command) throws ParseException {
        final Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new ManifestationNotFoundException(command.id));

        manifestation.setName(command.name);

        manifestation.setRegularTicketPrice(command.regularTicketPrice);
        manifestation.getTickets().forEach(ticket -> {
            if (ticket.getStatus() == TicketStatus.FREE)
                ticket.setPrice(command.regularTicketPrice);
        });

        manifestation.setHoldingDate(formatter.parse(command.holdingDate));
        manifestation.setDescription(command.description);
        manifestation.setStatus(ManifestationStatus.valueOf(command.status));
        manifestation.setType(ManifestationType.valueOf(command.type));

        manifestation.getImage().setLocation(base64ToImageLocation(command.imageBase64, command.imageType, manifestation.getId()));

        manifestationRepository.save(manifestation);
    }

    @Override
    public void updateLocation(UpdateLocationCommand command) {
        final Manifestation manifestation = manifestationRepository.findByIdAndArchivedFalse(command.manifestationId)
                .orElseThrow(() -> new ManifestationNotFoundException(command.manifestationId));

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

        // Addresses can be same if coordinates are different on some small decimal place
//        if (longitude1 == longitude2) {
//            if (latitude1 == latitude2) {
//                return isOverlap(holdingDate1, holdingDate2, 2);
//            }
//        }

        if (address1.equals(address2))
            return isOverlap(holdingDate1, holdingDate2, 2);
        return false;
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
