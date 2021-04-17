package useCase.customer;

import model.Customer;

import java.util.Collection;

public interface GetSuspiciousCustomersUseCase {
    Collection<Customer> getSuspiciousCustomers();
}
