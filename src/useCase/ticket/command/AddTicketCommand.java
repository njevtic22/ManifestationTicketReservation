package useCase.ticket.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddTicketCommand implements SelfValidating {
    public String type;

    public Long manifestationId;

    public AddTicketCommand(String type, Long manifestationId) {
        this.type = type;
        this.manifestationId = manifestationId;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (type == null || type.trim().isEmpty())
            throw new ConstraintViolationException("Ticket type must not be empty.");

        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");
    }
}
