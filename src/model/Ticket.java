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

    private static LongGenerator idGenerator;

    public Ticket(String appId, double price, TicketStatus status, TicketType type, boolean archived) {
        this(idGenerator.next(), appId, price, status, type, archived);
    }

    public Ticket(Long id, String appId, double price, TicketStatus status, TicketType type, boolean archived) {
        this.id = id;
        this.appId = appId;
        this.price = price;
        this.status = status;
        this.type = type;
        this.archived = archived;
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

    public Long getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
