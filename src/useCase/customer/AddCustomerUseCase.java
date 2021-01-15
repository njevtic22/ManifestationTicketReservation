package useCase.customer;

import useCase.customer.command.AddCustomerCommand;

import java.text.ParseException;

public interface AddCustomerUseCase {
    void addCustomer(AddCustomerCommand command) throws ParseException;
}
