package model;

import utility.generator.LongGenerator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Manifestation {
    private Long id;
    private String name;
    private long numberOfTickets;
    private double regularTicketPrice;
    private Date holdingDate;
    private ManifestationStatus status;
    private ManifestationType type;
    private boolean archived;

    private Location location;
    private Image image;
    private List<Ticket> tickets;

    private static LongGenerator idGenerator;

    public Manifestation(String name, long numberOfTickets, double regularTicketPrice, Date holdingDate, ManifestationStatus status, ManifestationType type, boolean archived, Location location, Image image) {
        this(
                idGenerator.next(),
                name,
                numberOfTickets,
                regularTicketPrice,
                holdingDate,
                status,
                type,
                archived,
                location,
                image
        );
    }

    public Manifestation(Long id, String name, long numberOfTickets, double regularTicketPrice, Date holdingDate, ManifestationStatus status, ManifestationType type, boolean archived, Location location, Image image) {
        this.id = id;
        this.name = name;
        this.numberOfTickets = numberOfTickets;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.location = location;
        this.image = image;
    }

    public Manifestation(String name, long numberOfTickets, double regularTicketPrice, Date holdingDate, ManifestationStatus status, ManifestationType type, boolean archived, Location location, Image image,  List<Ticket> tickets) {
        this(
                idGenerator.next(),
                name,
                numberOfTickets,
                regularTicketPrice,
                holdingDate,
                status,
                type,
                archived,
                location,
                image,
                tickets
        );
    }

    public Manifestation(Long id, String name, long numberOfTickets, double regularTicketPrice, Date holdingDate, ManifestationStatus status, ManifestationType type, boolean archived, Location location, Image image, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.numberOfTickets = numberOfTickets;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.location = location;
        this.image = image;
        this.tickets = tickets;
    }

    public static void initGenerator() {
        Manifestation.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Manifestation.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manifestation)) return false;
        Manifestation that = (Manifestation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(long numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getRegularTicketPrice() {
        return regularTicketPrice;
    }

    public void setRegularTicketPrice(double regularTicketPrice) {
        this.regularTicketPrice = regularTicketPrice;
    }

    public Date getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(Date holdingDate) {
        this.holdingDate = holdingDate;
    }

    public ManifestationStatus getStatus() {
        return status;
    }

    public void setStatus(ManifestationStatus status) {
        this.status = status;
    }

    public ManifestationType getType() {
        return type;
    }

    public void setType(ManifestationType type) {
        this.type = type;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
