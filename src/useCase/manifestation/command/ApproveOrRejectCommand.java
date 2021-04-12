package useCase.manifestation.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class ApproveOrRejectCommand implements SelfValidating {
    public Long id;
    public String newStatus;

    public ApproveOrRejectCommand(Long id, String newStatus) {
        this.id = id;
        this.newStatus = newStatus;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Salesman id must be positive number.");

        if (newStatus == null || newStatus.trim().isEmpty())
            throw new ConstraintViolationException("Status must not be empty.");
    }
}
