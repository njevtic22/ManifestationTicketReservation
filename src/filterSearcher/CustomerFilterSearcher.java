package filterSearcher;

import model.Customer;
import model.CustomerType;

import java.util.Collection;

public class CustomerFilterSearcher {
    public void filterByRole(String role, Collection<Customer> customers) {
        customers.removeIf(customer -> !customer.getClass().getSimpleName().toUpperCase().equals(role.toUpperCase()));
    }

    public void filterByType(CustomerType type, Collection<Customer> customers) {
        customers.removeIf(customer -> customer.getType() != type);
    }

    public void searchByName(String name, Collection<Customer> customers) {
        customers.removeIf(customer -> !customer.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public void searchBySurname(String surname, Collection<Customer> customers) {
        customers.removeIf(customer -> !customer.getSurname().toLowerCase().contains(surname.toLowerCase()));
    }

    public void searchByUsername(String username, Collection<Customer> customers) {
        customers.removeIf(customer -> !customer.getUsername().toLowerCase().contains(username.toLowerCase()));
    }
}
