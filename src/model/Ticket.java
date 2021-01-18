package model;

import utility.generator.LongGenerator;

import java.util.Objects;

public class Ticket {
    private Long id;
    private String appId;
    private double price;
    private TicketStatus status;
    private TicketType type;
    private boolean archived;

    private Customer customer;
    private Manifestation manifestation;

    private static LongGenerator idGenerator;

    public Ticket(String appId, double price, TicketStatus status, TicketType type, boolean archived, Manifestation manifestation, Customer customer) {
        this(idGenerator.next(), appId, price, status, type, archived, customer, manifestation);
    }

    public Ticket(Long id, String appId, double price, TicketStatus status, TicketType type, boolean archived, Customer customer, Manifestation manifestation) {
        this.id = id;
        this.appId = appId;
        setPrice(price);
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.customer = customer;
        this.manifestation = manifestation;
    }

    public Ticket(double price, TicketStatus status, TicketType type, boolean archived, Manifestation manifestation, Customer customer) {
        this(idGenerator.next(), price, status, type, archived, customer, manifestation);
    }

    public Ticket(Long id, double price, TicketStatus status, TicketType type, boolean archived, Customer customer, Manifestation manifestation) {
        this.id = id;
        setPrice(price);
        this.status = status;
        this.type = type;
        this.archived = archived;
        this.customer = customer;
        this.manifestation = manifestation;

        String strId = id.toString();
        String zero = "0";
        int zeroToRepeat = 10 - strId.length();
        this.appId = zero.repeat(zeroToRepeat) + strId;
    }

    public static void initGenerator() {
        Ticket.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        Ticket.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void archive() {
        this.archived = true;
    }

    public Long getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double regularPrice) {
        int multiplyBy = this.type.getMultiplyBy();
        this.price = regularPrice * multiplyBy;
    }

    public void setPriceDiscount(double discount) {
        this.price -= (this.price * discount);
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }
}
