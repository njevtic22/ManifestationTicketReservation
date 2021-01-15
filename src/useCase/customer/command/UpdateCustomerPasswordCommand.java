package useCase.customer.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateCustomerPasswordCommand implements SelfValidating {
    public Long id;
    public String password;

    public UpdateCustomerPasswordCommand(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        if (password == null || password.trim().isEmpty())
            throw new ConstraintViolationException("Passowrd must not be empty.");
    }
}
