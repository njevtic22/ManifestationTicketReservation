package useCase.withdrawalHistory.dto;

import model.WithdrawalHistory;

public class GetAllHistoriesDTO {
    private Long id;
    private String withdrawalDate;
    private String ticketId;
    private double price;
    private String type;
    private boolean archived;

    private String manifestation;

    public GetAllHistoriesDTO(WithdrawalHistory history, String parsedDate) {
        this.id = history.getId();
        this.withdrawalDate = parsedDate;
        this.ticketId = history.getTicketId();
        this.price = history.getPrice();
        this.type = history.getType().toString();
        this.archived = history.isArchived();
        this.manifestation = history.getManifestation().getName();
    }
}
