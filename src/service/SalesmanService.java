package service;

import exception.SalesmanNotFoundException;
import exception.UserNameTakenException;
import model.Admin;
import model.Customer;
import model.Gender;
import model.Salesman;
import model.User;
import repository.UserRepository;
import useCase.salesman.AddSalesmanUseCase;
import useCase.salesman.DeleteSalesmanUseCase;
import useCase.salesman.GetAllSalesmenUseCase;
import useCase.salesman.GetByIdSalesmanUseCase;
import useCase.salesman.UpdateSalesmanPasswordUseCase;
import useCase.salesman.UpdateSalesmanUseCase;
import useCase.salesman.command.AddSalesmanCommand;
import useCase.salesman.command.UpdateSalesmanCommand;
import useCase.salesman.command.UpdateSalesmanPasswordCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;

public class SalesmanService implements
        AddSalesmanUseCase,
        GetAllSalesmenUseCase,
        GetByIdSalesmanUseCase,
        UpdateSalesmanUseCase,
        UpdateSalesmanPasswordUseCase,
        DeleteSalesmanUseCase {
    private final SimpleDateFormat formatter;
    private final UserRepository<Salesman> salesmanRepository;
    private final UserRepository<Admin> adminRepository;
    private final UserRepository<Customer> customerRepository;

    public SalesmanService(SimpleDateFormat formatter, UserRepository<Salesman> salesmanRepository, UserRepository<Admin> adminRepository, UserRepository<Customer> customerRepository) {
        this.formatter = formatter;
        this.salesmanRepository = salesmanRepository;
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addSalesman(AddSalesmanCommand command) throws ParseException {
        if (isUsernameSalesmanTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameAdminTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameCustomerTaken(command.username))
            throw new UserNameTakenException(command.username);

        Salesman salesman = new Salesman(
                command.name,
                command.surname,
                command.username,
                command.password,
                formatter.parse(command.dateOfBirth),
                Gender.valueOf(command.gender),
                false
        );
        salesmanRepository.save(salesman);
    }

    @Override
    public Collection<Salesman> getAllSalesmen() {
        return salesmanRepository.findAllByArchivedFalse();
    }

    @Override
    public Salesman getByIdSalesman(Long id) {
        return salesmanRepository.findByIdAndArchivedFalse(id).orElseThrow(() -> new SalesmanNotFoundException(id));
    }

    @Override
    public void updateSalesman(UpdateSalesmanCommand command) throws ParseException {
        Salesman salesman = salesmanRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new SalesmanNotFoundException(command.id));

        if (isUsernameSalesmanTaken(command.username, salesman))
            throw new UserNameTakenException(command.username);

        if (isUsernameAdminTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameCustomerTaken(command.username))
            throw new UserNameTakenException(command.username);

        salesman.setName(command.name);
        salesman.setSurname(command.surname);
        salesman.setUsername(command.username);
        salesman.setDateOfBirth(formatter.parse(command.dateOfBirth));
        salesman.setGender(Gender.valueOf(command.gender));

        salesmanRepository.save(salesman);
    }

    @Override
    public void updatePassword(UpdateSalesmanPasswordCommand command) {
        Salesman salesman = salesmanRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new SalesmanNotFoundException(command.id));

        salesman.setPassword(command.password);

        salesmanRepository.save(salesman);
    }

    @Override
    public void deleteSalesman(Long id) {
        Salesman salesman = salesmanRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new SalesmanNotFoundException(id));

        salesman.archive();

        salesmanRepository.save(salesman);
    }

    private boolean isUsernameAdminTaken(String usernameToValidate) {
        Optional<Admin> adminOptional = adminRepository.findByUserName(usernameToValidate);
        return adminOptional.isPresent();
    }

    private boolean isUsernameSalesmanTaken(String usernameToValidate) {
        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(usernameToValidate);
        return salesmanOptional.isPresent();
    }

    private boolean isUsernameSalesmanTaken(String usernameToValidate, Salesman userToCheckIsSame) {
        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(usernameToValidate);
        return salesmanOptional.isPresent() && !salesmanOptional.get().getUsername().equals(userToCheckIsSame.getUsername());
    }

    private boolean isUsernameCustomerTaken(String usernameToValidate) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(usernameToValidate);
        return customerOptional.isPresent();
    }
}
