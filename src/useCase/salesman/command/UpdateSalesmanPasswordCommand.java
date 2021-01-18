package useCase.salesman.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateSalesmanPasswordCommand implements SelfValidating {
    public Long id;
    public String password;

    public UpdateSalesmanPasswordCommand(Long id, String password) {
        this.id = id;
        this.password = password;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        if (password == null || password.trim().isEmpty())
            throw new ConstraintViolationException("Passowrd must not be empty.");
    }
}