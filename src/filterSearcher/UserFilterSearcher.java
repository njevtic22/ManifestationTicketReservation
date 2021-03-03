package filterSearcher;

import model.Customer;
import model.CustomerType;
import model.User;

import java.util.Collection;

public class UserFilterSearcher {
    public void filterByRole(String role, Collection<User> users) {
        users.removeIf(user -> !user.getClass().getSimpleName().toUpperCase().equals(role.toUpperCase()));
    }

    public void filterByType(CustomerType type, Collection<User> users) {
        users.removeIf(user -> {
            if (!(user instanceof Customer))
                return true;

            Customer customer = (Customer) user;
            if (customer.getType() != type)
                return true;

            return false;
        });
    }

    public void searchByName(String name, Collection<User> users) {
        users.removeIf(user -> !user.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public void searchBySurname(String surname, Collection<User> users) {
        users.removeIf(user -> !user.getSurname().toLowerCase().contains(surname.toLowerCase()));
    }

    public void searchByUsername(String username, Collection<User> users) {
        users.removeIf(user -> !user.getUsername().toLowerCase().contains(username.toLowerCase()));
    }
}
