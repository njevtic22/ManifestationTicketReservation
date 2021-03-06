package repository;

import model.Customer;
import model.Salesman;
import model.User;

import java.util.Optional;

public interface AuthenticationRepository {
    Optional<User> findById(Long id);
    Optional<User> findByIdAndArchivedFalse(Long id);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameAndArchivedFalse(String userName);

    void save(User user);
    void registerSalesman(Salesman salesman);
    void registerCustomer(Customer customer);
}
