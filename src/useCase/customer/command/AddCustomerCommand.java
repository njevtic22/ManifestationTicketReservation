package useCase.customer.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddCustomerCommand implements SelfValidating {
    public String name;
    public String surname;
    public String username;
    public String password;
    public String dateOfBirth;
    public String gender;

    public AddCustomerCommand(String name, String surname, String username, String password, String dateOfBirth, String gender) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (name == null || name.trim().isEmpty())
            throw new ConstraintViolationException("Name must not be empty.");

        if (surname == null || surname.trim().isEmpty())
            throw new ConstraintViolationException("Surname must not be empty.");

        if (username == null || username.trim().isEmpty())
            throw new ConstraintViolationException("Username must not be empty.");

        if (password == null || password.trim().isEmpty())
            throw new ConstraintViolationException("Password must not be empty.");

        if (dateOfBirth == null || dateOfBirth.trim().isEmpty())
            throw new ConstraintViolationException("Date of birth must not be empty.");

        if (gender == null || gender.trim().isEmpty())
            throw new ConstraintViolationException("Gender must not be empty.");
    }
}
