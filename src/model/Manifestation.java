package model;

import utility.generator.LongGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Manifestation {
    private Long id;
    private String name;
    private long numberOfTicketsLeft;
    private double regularTicketPrice;
    private Date holdingDate;
    private String description;
    private ManifestationStatus status;
    private ManifestationType type;
    private boolean archived;

    private Location location;
    private Image image;
    private List<Ticket> tickets;
    private List<Review> reviews;

    private static LongGenerator idGenerator;

    public Manifestation(
            String name,
            long numberOfTicketsLeft,
            double regularTicketPrice,
            Date holdingDate,
            String description,
            ManifestationStatus status,
            ManifestationType type,
            boolean archived,
            Location location,
            Image image
    ) {
        this(
                idGenerator.next(),
                name,
                numberOfTicketsLeft,
                regularTicketPrice,
                holdingDate,
                description,
                status,
                type,
                archived,
                location,
                image
        );
    }

    public Manifestation(
            Long id,
            String name,
            long numberOfTicketsLeft,
            double regularTicketPrice,
            Date holdingDate,
            String description,
            ManifestationStatus status,
            ManifestationType type,
            boolean archived,
            Location location,
            Image image
    ) {
        this.id = id;
        this.name = name;
        this.numberOfTicketsLeft = numberOfTicketsLeft;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.description = description;
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.location = location;
        this.image = image;
        this.tickets = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Manifestation(
            String name,
            long numberOfTicketsLeft,
            double regularTicketPrice,
            Date holdingDate,
            String description,
            ManifestationStatus status,
            ManifestationType type,
            boolean archived,
            Location location,
            Image image,
            List<Ticket> tickets,
            List<Review> reviews
    ) {
        this(
                idGenerator.next(),
                name,
                numberOfTicketsLeft,
                regularTicketPrice,
                holdingDate,
                description,
                status,
                type,
                archived,
                location,
                image,
                tickets,
                reviews
        );
    }

    public Manifestation(
            Long id,
            String name,
            long numberOfTicketsLeft,
            double regularTicketPrice,
            Date holdingDate,
            String description,
            ManifestationStatus status,
            ManifestationType type,
            boolean archived,
            Location location,
            Image image,
            List<Ticket> tickets,
            List<Review> reviews
    ) {
        this.id = id;
        this.name = name;
        this.numberOfTicketsLeft = numberOfTicketsLeft;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.description = description;
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.location = location;
        this.image = image;
        this.tickets = tickets;
        this.reviews = reviews;
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

    public void archive() {
        this.archived = true;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
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

    public long getNumberOfTicketsLeft() {
        return numberOfTicketsLeft;
    }

    public void setNumberOfTicketsLeft(long numberOfTicketsLeft) {
        this.numberOfTicketsLeft = numberOfTicketsLeft;
    }

    public double getRegularTicketPrice() {
        return regularTicketPrice;
    }

    public void setRegularTicketPrice(double regularTicketPrice) {
        this.regularTicketPrice = regularTicketPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
