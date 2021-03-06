package useCase.authentication.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class ChangePasswordCommand implements SelfValidating {
    public Long id;
    public String oldPassword;
    public String newPassword;
    public String repPassword;

    public ChangePasswordCommand(Long id, String oldPassword, String newPassword, String repPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repPassword = repPassword;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        if (oldPassword == null || oldPassword.trim().isEmpty())
            throw new ConstraintViolationException("Old password must not be empty.");

        if (newPassword == null || newPassword.trim().isEmpty())
            throw new ConstraintViolationException("New password must not be empty.");

        if (repPassword == null || repPassword.trim().isEmpty())
            throw new ConstraintViolationException("Repeated password must not be empty.");
    }
}
