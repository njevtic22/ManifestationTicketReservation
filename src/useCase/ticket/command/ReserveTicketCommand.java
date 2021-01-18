package useCase.ticket.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class ReserveTicketCommand implements SelfValidating {
    public Long ticketId;
    public Long customerId;

    public ReserveTicketCommand(Long ticketId, Long customerId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
    }

    @Override
    public void validateSelf() {
        if (ticketId == null || ticketId <= 0)
            throw new ConstraintViolationException("Ticket id must be positive number.");

        if (customerId == null || customerId <= 0)
            throw new ConstraintViolationException("Customer id must be positive number.");
    }
}
