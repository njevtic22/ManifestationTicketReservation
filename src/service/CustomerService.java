package service;

import exception.CustomerNotFoundException;
import exception.UserNameTakenException;
import model.Admin;
import model.Customer;
import model.CustomerType;
import model.Gender;
import model.Salesman;
import model.WithdrawalHistory;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        Collection<Customer> customers = customerRepository.findAllByArchivedFalse();


        Collection<Customer> suspiciousCustomers = new ArrayList<>();


        for (Customer customer : customers) {
            if (customer.getWithdrawalHistory().size() == 0)
                continue;


            List<Integer> months = new ArrayList<>(customer.getWithdrawalHistory().size());

            for (WithdrawalHistory history : customer.getWithdrawalHistory()) {

                String formattedDate = formatter.format(history.getWithdrawalDate());
                String[] splitDate = formattedDate.split("\\.");
                String monthStr = splitDate[1];

                int month = Integer.parseInt(monthStr);
                months.add(month);
            }


            Collections.sort(months);

            int withdrawalNum = 1;
            int previousMonth = months.get(0);
            for (int i = 1; i < months.size(); i++) {
                int currentMonth = months.get(i);
                if (previousMonth == currentMonth) {
                    withdrawalNum++;
                }

                if (withdrawalNum > 5) {
                    suspiciousCustomers.add(customer);
                    break;
                }

                previousMonth = currentMonth;
            }
        }

        return suspiciousCustomers;
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
