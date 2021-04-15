package useCase.ticket.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class ReserveTicketCommand implements SelfValidating {
    public Long customerId;
    public Long manifestationId;
    public Long numberOfRegularTickets;
    public Long numberOfFanTickets;
    public Long numberOfVipTickets;

    public ReserveTicketCommand(Long customerId, Long manifestationId, Long numberOfRegularTickets, Long numberOfFanTickets, Long numberOfVipTickets) {
        this.customerId = customerId;
        this.manifestationId = manifestationId;
        this.numberOfRegularTickets = numberOfRegularTickets;
        this.numberOfFanTickets = numberOfFanTickets;
        this.numberOfVipTickets = numberOfVipTickets;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (customerId == null || customerId <= 0)
            throw new ConstraintViolationException("Customer id must be positive number.");

        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");

        if (numberOfRegularTickets == null || numberOfRegularTickets <= 0)
            throw new ConstraintViolationException("Number of regular tickets must be positive number.");

        if (numberOfFanTickets == null || numberOfFanTickets <= 0)
            throw new ConstraintViolationException("Number of fan pit tickets must be positive number.");

        if (numberOfVipTickets == null || numberOfVipTickets <= 0)
            throw new ConstraintViolationException("Number of vip tickets must be positive number.");
    }
}
