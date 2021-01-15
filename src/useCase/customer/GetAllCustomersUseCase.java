package useCase.customer;

import model.Customer;

import java.util.Collection;

public interface GetAllCustomersUseCase {
    Collection<Customer> getAllCustomers();
}
