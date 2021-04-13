package useCase.ticket.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddTicketCommand implements SelfValidating {
    public Long manifestationId;
    public Long numberOfRegularTickets;
    public Long numberOfFanPitTickets;
    public Long numberOfVIPTickets;

    public AddTicketCommand(Long manifestationId, Long numberOfRegularTickets, Long numberOfFanPitTickets, Long numberOfVIPTickets) {
        this.manifestationId = manifestationId;
        this.numberOfRegularTickets = numberOfRegularTickets;
        this.numberOfFanPitTickets = numberOfFanPitTickets;
        this.numberOfVIPTickets = numberOfVIPTickets;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");

        if (numberOfRegularTickets == null || numberOfRegularTickets < 0)
            throw new ConstraintViolationException("Number of regular tickets must be positive number or zero.");

        if (numberOfFanPitTickets == null || numberOfFanPitTickets < 0)
            throw new ConstraintViolationException("Number of Fan Pit tickets must be positive number or zero.");

        if (numberOfVIPTickets == null || numberOfVIPTickets < 0)
            throw new ConstraintViolationException("Number of vip tickets must be positive number or zero.");
    }
}
