package filter;

import model.Customer;
import model.CustomerType;
import model.User;

import java.util.Collection;

public class UserFilter {
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
}
