package useCase.salesman.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateSalesmanCommand implements SelfValidating {
    public Long id;
    public String name;
    public String surname;
    public String username;
    public String dateOfBirth;
    public String gender;

    public UpdateSalesmanCommand(Long id, String name, String surname, String username, String dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        if (name == null || name.trim().isEmpty())
            throw new ConstraintViolationException("Name must not be empty.");

        if (surname == null || surname.trim().isEmpty())
            throw new ConstraintViolationException("Surname must not be empty.");

        if (username == null || username.trim().isEmpty())
            throw new ConstraintViolationException("Username must not be empty.");

        if (dateOfBirth == null || dateOfBirth.trim().isEmpty())
            throw new ConstraintViolationException("Date of birth must not be empty.");

        if (gender == null || gender.trim().isEmpty())
            throw new ConstraintViolationException("Gender must not be empty.");
    }
}
