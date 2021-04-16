package useCase.withdrawalHistory.dto;

import model.WithdrawalHistory;

public class GetAllHistoriesDTO {
    private Long id;
    private String ticketId;

    private String withdrawalDate; // search --
    private double price;         // search --
    private String ticketType;   // filter --

    private String manifestation;           // search --
    private String manifestationStatus;    // filter --
    private String manifestationType;     // filter --
    private String manifestationDate;    // search

    public GetAllHistoriesDTO(WithdrawalHistory history, String parsedDate, String manifestationDate) {
        this.id = history.getId();
        this.withdrawalDate = parsedDate;
        this.ticketId = history.getTicketId();
        this.price = history.getPrice();
        this.ticketType = history.getType().toString();
        this.manifestation = history.getManifestation().getName();
        this.manifestationDate = manifestationDate;
        this.manifestationStatus = history.getManifestation().getStatus().toString();
        this.manifestationType = history.getManifestation().getType().toString();
    }
}
