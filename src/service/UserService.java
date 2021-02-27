package service;

import model.Admin;
import model.Customer;
import model.Salesman;
import model.User;
import repository.UserRepository;
import useCase.user.GetAllUsersUseCase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class UserService implements GetAllUsersUseCase {
    private final SimpleDateFormat formatter;
    private final UserRepository<Admin> adminRepository;
    private final UserRepository<Salesman> salesmanRepository;
    private final UserRepository<Customer> customerRepository;

    public UserService(SimpleDateFormat formatter, UserRepository<Admin> adminRepository, UserRepository<Salesman> salesmanRepository, UserRepository<Customer> customerRepository) {
        this.formatter = formatter;
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<User> getAllUsers() {
        long usersCount =
                adminRepository.count() +
                salesmanRepository.count() +
                customerRepository.count();
        ArrayList<User> users = new ArrayList<>((int) usersCount);

        Collection<Admin> admins = adminRepository.findAllByArchivedFalse();
        Collection<Salesman> salesmen = salesmanRepository.findAllByArchivedFalse();
        Collection<Customer> customers = customerRepository.findAllByArchivedFalse();

        users.addAll(admins);
        users.addAll(salesmen);
        users.addAll(customers);

        return users;
    }
}
