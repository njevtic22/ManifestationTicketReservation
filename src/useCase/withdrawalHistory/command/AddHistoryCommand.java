package useCase.withdrawalHistory.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddHistoryCommand implements SelfValidating {
    public String withdrawalDate;
    public String ticketId;
    public double price;
    public String type;

    public Long manifestationId;

    @Override
    public void validateSelf() {
        if (withdrawalDate == null || withdrawalDate.trim().isEmpty())
            throw new ConstraintViolationException("Date must not be empty.");

        if (ticketId == null || ticketId.trim().isEmpty())
            throw new ConstraintViolationException("Ticket id must not be empty.");

        if (price <= 0)
            throw new ConstraintViolationException("Price must be positive number");

        if (type == null || type.trim().isEmpty())
            throw new ConstraintViolationException("Ticket type must not be empty.");

        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");
    }
}
