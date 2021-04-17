package service;

import exception.CustomerNotFoundException;
import exception.UserNameTakenException;
import model.Admin;
import model.Customer;
import model.CustomerType;
import model.Gender;
import model.Salesman;
import repository.UserRepository;
import useCase.customer.AddCustomerUseCase;
import useCase.customer.DeleteCustomerUseCase;
import useCase.customer.GetAllCustomersUseCase;
import useCase.customer.GetByIdCustomerUseCase;
import useCase.customer.GetSuspiciousCustomersUseCase;
import useCase.customer.UpdateCustomerUseCase;
import useCase.customer.command.AddCustomerCommand;
import useCase.customer.command.UpdateCustomerCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;

public class CustomerService implements
        AddCustomerUseCase,
        GetAllCustomersUseCase,
        GetByIdCustomerUseCase,
        UpdateCustomerUseCase,
        DeleteCustomerUseCase,
        GetSuspiciousCustomersUseCase
{
    private final SimpleDateFormat formatter;
    private final UserRepository<Customer> customerRepository;
    private final UserRepository<Salesman> salesmanRepository;
    private final UserRepository<Admin> adminRepository;

    public CustomerService(SimpleDateFormat formatter, UserRepository<Customer> customerRepository, UserRepository<Salesman> salesmanRepository, UserRepository<Admin> adminRepository) {
        this.formatter = formatter;
        this.customerRepository = customerRepository;
        this.salesmanRepository = salesmanRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void addCustomer(AddCustomerCommand command) throws ParseException {
        if (isUsernameCustomerTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameAdminTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameSalesmanTaken(command.username))
            throw new UserNameTakenException(command.username);

        Customer customer = new Customer(
                command.name,
                command.surname,
                command.username,
                command.password,
                formatter.parse(command.dateOfBirth),
                Gender.valueOf(command.gender),
                false,
                0,
                CustomerType.BRONZE
        );
        customerRepository.save(customer);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return customerRepository.findAllByArchivedFalse();
    }

    @Override
    public Customer getByIdCustomer(Long id) {
        return customerRepository.findByIdAndArchivedFalse(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public void updateCustomer(UpdateCustomerCommand command) throws ParseException {
        Customer customer = customerRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new CustomerNotFoundException(command.id));

        if (isUsernameCustomerTaken(command.username, customer))
            throw new UserNameTakenException(command.username);

        if (isUsernameSalesmanTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameAdminTaken(command.username))
            throw new UserNameTakenException(command.username);

        customer.setName(command.name);
        customer.setSurname(command.surname);
        customer.setUsername(command.username);
        customer.setDateOfBirth(formatter.parse(command.dateOfBirth));
        customer.setGender(Gender.valueOf(command.gender));

        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.archive();

        customerRepository.save(customer);
    }

    @Override
    public Collection<Customer> getSuspiciousCustomers() {
        // TODO: change this
        return customerRepository.findAllByArchivedFalse();
    }

    private boolean isUsernameAdminTaken(String usernameToValidate) {
        Optional<Admin> adminOptional = adminRepository.findByUserName(usernameToValidate);
        return adminOptional.isPresent();
    }

    private boolean isUsernameSalesmanTaken(String usernameToValidate) {
        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(usernameToValidate);
        return salesmanOptional.isPresent();
    }

    private boolean isUsernameCustomerTaken(String usernameToValidate) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(usernameToValidate);
        return customerOptional.isPresent();
    }

    private boolean isUsernameCustomerTaken(String usernameToValidate, Customer userToCheckIsSame) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(usernameToValidate);
        return customerOptional.isPresent() && !customerOptional.get().getUsername().equals(userToCheckIsSame.getUsername());
    }
}
