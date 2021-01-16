package useCase.authentication;

import model.Customer;
import useCase.authentication.command.RegisterUserCommand;

import java.text.ParseException;

public interface RegisterCustomerAuthenticationCase {
    Customer registerCustomer(RegisterUserCommand command) throws ParseException;
}
