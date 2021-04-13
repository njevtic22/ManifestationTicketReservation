package filterSearcher;

import model.ManifestationStatus;
import model.Ticket;
import model.TicketStatus;
import model.TicketType;

import java.util.Collection;
import java.util.Date;

public class TicketFilterSearcher {
    public void filterByType(TicketType type, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getType() != type);
    }

    public void filterByTicketStatus(TicketStatus status, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getStatus() != status);
    }

    public void filterByManifestationStatus(ManifestationStatus status, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getManifestation().getStatus() != status);
    }

    public void searchByManifestation(String manifestationName, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getManifestation().getName().toLowerCase().contains(manifestationName.toLowerCase()));
    }

    public void searchByPriceFrom(long priceFrom, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> priceFrom > ticket.getPrice());
    }

    public void searchByPriceTo(long priceTo, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getPrice() > priceTo);
    }

    public void searchByDateFrom(Date dateFrom, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getManifestation().getHoldingDate().before(dateFrom));
    }

    public void searchByDateTo(Date dateTo, Collection<Ticket> tickets) {
        tickets.removeIf(ticket -> ticket.getManifestation().getHoldingDate().after(dateTo));
    }
}
