package useCase.ticket.dto;

import model.Ticket;

public class GetAllTicketsDTO {
    public long id;
    public String appId;
    public double price;
    public String status;
    public String type;

    public String customer;
    public String manifestation;

    public GetAllTicketsDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.appId = ticket.getAppId();
        this.price = ticket.getPrice();
        this.status = ticket.getStatus().toString();
        this.type = ticket.getType().toString();
        this.customer = ticket.getCustomer().getName() + " " + ticket.getCustomer().getSurname();
        this.manifestation = ticket.getManifestation().getName();
    }
}
