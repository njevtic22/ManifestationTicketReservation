package repository;

import exception.InvalidRoleException;
import model.Admin;
import model.Customer;
import model.Salesman;
import model.User;

import java.util.Optional;

public class AuthenticationJSONRepository implements AuthenticationRepository {
    private final UserRepository<Admin> adminRepository;
    private final UserRepository<Salesman> salesmanRepository;
    private final UserRepository<Customer> customerRepository;

    public AuthenticationJSONRepository(UserRepository<Admin> adminRepository, UserRepository<Salesman> salesmanRepository, UserRepository<Customer> customerRepository) {
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent())
             return Optional.of(adminOptional.get());

        Optional<Salesman> salesmanOptional = salesmanRepository.findById(id);
        if (salesmanOptional.isPresent())
            return Optional.of(salesmanOptional.get());

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent())
            return Optional.of(customerOptional.get());

        return Optional.empty();
    }

    @Override
    public Optional<User> findByIdAndArchivedFalse(Long id) {
        Optional<User> userOptional = findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isArchived())
                return Optional.empty();
        }
        return userOptional;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Optional<Admin> adminOptional = adminRepository.findByUserName(userName);
        if (adminOptional.isPresent())
            return Optional.of(adminOptional.get());

        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(userName);
        if (salesmanOptional.isPresent())
            return Optional.of(salesmanOptional.get());

        Optional<Customer> customerOptional = customerRepository.findByUserName(userName);
        if (customerOptional.isPresent())
            return Optional.of(customerOptional.get());

        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserNameAndArchivedFalse(String userName) {
        Optional<User> userOptional = findByUserName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isArchived())
                return Optional.empty();
        }
        return userOptional;
    }

    @Override
    public void save(User user) {
        if (user instanceof Admin)
            adminRepository.save((Admin) user);
        else if (user instanceof Salesman)
            salesmanRepository.save((Salesman) user);
        else if (user instanceof Customer)
            customerRepository.save((Customer) user);
        else
            throw new InvalidRoleException("Unknown user role");
    }

    @Override
    public void registerSalesman(Salesman salesman) {
        salesmanRepository.save(salesman);
    }

    @Override
    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
