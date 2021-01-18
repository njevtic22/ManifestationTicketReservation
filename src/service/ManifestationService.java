package service;

import exception.ManifestationNotFoundException;
import exception.PlaceAndDateTakenException;
import exception.SalesmanNotFoundException;
import model.*;
import repository.Repository;
import repository.UserRepository;
import useCase.manifestation.AddManifestationUseCase;
import useCase.manifestation.DeleteManifestationUseCase;
import useCase.manifestation.GetAllManifestationsUseCase;
import useCase.manifestation.GetByIdManifestationUseCase;
import useCase.manifestation.UpdateLocationUseCase;
import useCase.manifestation.UpdateManifestationUseCase;
import useCase.manifestation.command.AddManifestationCommand;
import useCase.manifestation.command.UpdateLocationCommand;
import useCase.manifestation.command.UpdateManifestationCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

public class ManifestationService implements
        AddManifestationUseCase,
        GetAllManifestationsUseCase,
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

    @Override
    public void addManifestation(AddManifestationCommand command) throws ParseException {
        Manifestation manifestation = new Manifestation(
                command.name,
                0,
                command.regularTicketPrice,
                formatter.parse(command.holdingDate),
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
                        command.imageLocation
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
        Collection<Manifestation> allManifestations;

        if (user instanceof Salesman)
            allManifestations = ((Salesman) user).getManifestations()
                    .stream()
                    .filter(manifestation -> !manifestation.isArchived())
                    .collect(Collectors.toList());
        else
            allManifestations = manifestationRepository.findAllByArchivedFalse();

        return allManifestations;
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
        manifestation.setStatus(ManifestationStatus.valueOf(command.status));
        manifestation.setType(ManifestationType.valueOf(command.type));

        manifestation.getImage().setLocation(command.imageLocation);

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
        final double longitude1 = manifestation1.getLocation().getLongitude();
        final double latitude1 = manifestation1.getLocation().getLatitude();

        final Date holdingDate2 = manifestation2.getHoldingDate();
        final double longitude2 = manifestation2.getLocation().getLongitude();
        final double latitude2 = manifestation2.getLocation().getLatitude();

        if (longitude1 == longitude2) {
            if (latitude1 == latitude2) {
                return isOverlap(holdingDate1, holdingDate2, 2);
            }
        }
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
