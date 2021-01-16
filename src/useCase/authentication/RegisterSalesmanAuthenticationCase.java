package useCase.authentication;

import model.Salesman;
import useCase.authentication.command.RegisterUserCommand;

import java.text.ParseException;

public interface RegisterSalesmanAuthenticationCase {
    void registerSalesman(RegisterUserCommand command) throws ParseException;
}
