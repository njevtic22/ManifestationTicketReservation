package useCase.customer;

import model.Customer;

public interface GetByIdCustomerUseCase {
    Customer getByIdCustomer(Long id);
}
