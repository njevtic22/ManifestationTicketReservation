package useCase.ticket.dto;

import model.Ticket;
import model.TicketStatus;

public class GetAllTicketsDTO {
    public long id;
    public String appId;
    public double price;
    public String status;
    public String type;

    public String customer;
    public String manifestation;
    public long manifestationId;

    public GetAllTicketsDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.appId = ticket.getAppId();
        this.price = ticket.getPrice();
        this.status = ticket.getStatus().toString();
        this.type = ticket.getType().toString();
        this.manifestation = ticket.getManifestation().getName();
        this.manifestationId = ticket.getManifestation().getId();


        if (ticket.getStatus() == TicketStatus.FREE)
            this.customer = ticket.getCustomer().getName() + " " + ticket.getCustomer().getSurname();
        else
            this.customer = null;
    }
}
