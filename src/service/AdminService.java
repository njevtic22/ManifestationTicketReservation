package service;

import exception.AdminNotFoundException;
import exception.UserNameTakenException;
import model.Admin;
import model.Customer;
import model.Gender;
import model.Salesman;
import repository.UserRepository;
import useCase.admin.AddAdminUseCase;
import useCase.admin.DeleteAdminUseCase;
import useCase.admin.GetAllAdminsUseCase;
import useCase.admin.GetByIdAdminUseCase;
import useCase.admin.UpdateAdminUseCase;
import useCase.admin.command.AddAdminCommand;
import useCase.admin.command.UpdateAdminCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;

public class AdminService implements
        AddAdminUseCase,
        GetAllAdminsUseCase,
        GetByIdAdminUseCase,
        UpdateAdminUseCase,
        DeleteAdminUseCase {
    private final SimpleDateFormat formatter;
    private final UserRepository<Admin> adminRepository;
    private final UserRepository<Salesman> salesmanRepository;
    private final UserRepository<Customer> customerRepository;


    public AdminService(SimpleDateFormat formatter, UserRepository<Admin> adminRepository, UserRepository<Salesman> salesmanRepository, UserRepository<Customer> customerRepository) {
        this.formatter = formatter;
        this.adminRepository = adminRepository;
        this.salesmanRepository = salesmanRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void addAdmin(AddAdminCommand command) throws ParseException {
        if (isUsernameAdminTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameSalesmanTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameCustomerTaken(command.username))
            throw new UserNameTakenException(command.username);

        Admin admin = new Admin(
                command.name,
                command.surname,
                command.username,
                command.password,
                formatter.parse(command.dateOfBirth),
                Gender.valueOf(command.gender),
                false
        );
        adminRepository.save(admin);
    }

    @Override
    public Collection<Admin> getAllAdmins() {
        return adminRepository.findAllByArchivedFalse();
    }

    @Override
    public Admin getByIdAdmin(Long id) {
        return adminRepository.findByIdAndArchivedFalse(id).orElseThrow(() -> new AdminNotFoundException(id));
    }

    @Override
    public void updateAdmin(UpdateAdminCommand command) throws ParseException {
        Admin admin = adminRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new AdminNotFoundException(command.id));

        if (isUsernameAdminTaken(command.username, admin))
            throw new UserNameTakenException(command.username);

        if (isUsernameSalesmanTaken(command.username))
            throw new UserNameTakenException(command.username);

        if (isUsernameCustomerTaken(command.username))
            throw new UserNameTakenException(command.username);

        admin.setName(command.name);
        admin.setSurname(command.surname);
        admin.setUsername(command.username);
        admin.setDateOfBirth(formatter.parse(command.dateOfBirth));
        admin.setGender(Gender.valueOf(command.gender));

        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new AdminNotFoundException(id));

        admin.archive();

        adminRepository.save(admin);
    }

    private boolean isUsernameAdminTaken(String usernameToValidate) {
        Optional<Admin> adminOptional = adminRepository.findByUserName(usernameToValidate);
        return adminOptional.isPresent();
    }

    private boolean isUsernameAdminTaken(String usernameToValidate, Admin userToCheckIsSame) {
        Optional<Admin> adminOptional = adminRepository.findByUserName(usernameToValidate);
        return adminOptional.isPresent() && !adminOptional.get().getUsername().equals(userToCheckIsSame.getUsername());
    }

    private boolean isUsernameSalesmanTaken(String usernameToValidate) {
        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(usernameToValidate);
        return salesmanOptional.isPresent();
    }

    private boolean isUsernameCustomerTaken(String usernameToValidate) {
        Optional<Customer> customerOptional = customerRepository.findByUserName(usernameToValidate);
        return customerOptional.isPresent();
    }
}
