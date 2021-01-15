package useCase.customer;

import useCase.customer.command.UpdateCustomerCommand;

import java.text.ParseException;

public interface UpdateCustomerUseCase {
    void updateCustomer(UpdateCustomerCommand command) throws ParseException;
}
