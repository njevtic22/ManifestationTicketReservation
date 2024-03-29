package model;

import utility.generator.LongGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Manifestation implements Cloneable {
    private Long id;
    private String name;
    private long maxNumberOfTickets;
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
            long maxNumberOfTickets,
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
                maxNumberOfTickets,
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
            long maxNumberOfTickets,
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
        this.maxNumberOfTickets = maxNumberOfTickets;
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
            long maxNumberOfTickets,
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
                maxNumberOfTickets,
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
            long maxNumberOfTickets,
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
        this.maxNumberOfTickets = maxNumberOfTickets;
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
    public Manifestation clone() throws CloneNotSupportedException {
        super.clone();
        return new Manifestation(
                this.id,
                this.name,
                this.maxNumberOfTickets,
                this.regularTicketPrice,
                this.holdingDate,
                this.description,
                this.status,
                this.type,
                this.archived,
                // Shallow copy
                this.location,
                this.image,
                this.tickets,
                this.reviews
        );
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

    public boolean isSoldOut() {

        long free = tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE && !ticket.isArchived())
                .count();

        return free == 0;
    }

    public long getNumberOfTicketsLeft() {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE && !ticket.isArchived())
                .count();
    }

    public long getNumberOfRegularTicketsLeft() {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE && !ticket.isArchived() && ticket.getType() == TicketType.REGULAR)
                .count();
    }

    public long getNumberOfFanTicketsLeft() {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE && !ticket.isArchived() && ticket.getType() == TicketType.FAN_PIT)
                .count();
    }

    public long getNumberOfVipTicketsLeft() {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE && !ticket.isArchived() && ticket.getType() == TicketType.VIP)
                .count();
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

    public long getMaxNumberOfTickets() {
        return maxNumberOfTickets;
    }

    public void setMaxNumberOfTickets(long maxNumberOfTickets) {
        this.maxNumberOfTickets = maxNumberOfTickets;
    }

    public double getRegularTicketPrice() {
        return regularTicketPrice;
    }

    public double getFanTicketPrice() {
        int multiplyBy = TicketType.FAN_PIT.getMultiplyBy();
        return regularTicketPrice * multiplyBy;
    }

    public double getVipTicketPrice() {
        int multiplyBy = TicketType.VIP.getMultiplyBy();
        return regularTicketPrice * multiplyBy;
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

    public List<Ticket> getFreeTickets() {
        return tickets
                .stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.FREE)
                .collect(Collectors.toList());
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
