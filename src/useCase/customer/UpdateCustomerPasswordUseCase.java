package useCase.customer;

import useCase.customer.command.UpdateCustomerPasswordCommand;

public interface UpdateCustomerPasswordUseCase {
    void updatePassword(UpdateCustomerPasswordCommand command);
}
