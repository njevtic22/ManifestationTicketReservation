package model;

import utility.generator.LongGenerator;

import java.util.Date;
import java.util.Objects;

public class WithdrawalHistory {
    private Long id;
    private Date withdrawalDate;
    private String ticketId;
    private double price;
    private TicketType type;
    private boolean archived;

    private Manifestation manifestation;

    private static LongGenerator idGenerator;

    public WithdrawalHistory(Date withdrawalDate, String ticketId, double price, TicketType type, boolean archived, Manifestation manifestation) {
        this(idGenerator.next(), withdrawalDate, ticketId, price, type, archived, manifestation);
    }

    public WithdrawalHistory(Long id, Date withdrawalDate, String ticketId, double price, TicketType type, boolean archived, Manifestation manifestation) {
        this.id = id;
        this.withdrawalDate = withdrawalDate;
        this.ticketId = ticketId;
        this.price = price;
        this.type = type;
        this.archived = archived;
        this.manifestation = manifestation;
    }

    public WithdrawalHistory(Date withdrawalDate, Ticket ticket, boolean archived, Manifestation manifestation) {
        this(idGenerator.next(), withdrawalDate, ticket, archived, manifestation);
    }

    public WithdrawalHistory(Long id, Date withdrawalDate, Ticket ticket, boolean archived, Manifestation manifestation) {
        this.id = id;
        this.withdrawalDate = withdrawalDate;
        this.ticketId = ticket.getAppId();
        this.price = ticket.getPrice();
        this.type = ticket.getType();
        this.archived = archived;
        this.manifestation = manifestation;
    }

    public static void initGenerator() {
        WithdrawalHistory.initGenerator(0L);
    }

    public static void initGenerator(Long start) {
        WithdrawalHistory.idGenerator = new LongGenerator(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WithdrawalHistory)) return false;
        WithdrawalHistory that = (WithdrawalHistory) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }
}
