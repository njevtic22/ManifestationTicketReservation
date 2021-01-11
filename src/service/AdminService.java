package service;

import exception.AdminNotFoundException;
import exception.UserNameTakenException;
import model.Admin;
import model.Gender;
import repository.UserRepository;
import useCase.admin.AddAdminUseCase;
import useCase.admin.DeleteAdminUseCase;
import useCase.admin.GetAllAdminsUseCase;
import useCase.admin.GetByIdAdminUseCase;
import useCase.admin.UpdateAdminUseCase;
import useCase.admin.UpdatePasswordUseCase;
import useCase.admin.command.AddAdminCommand;
import useCase.admin.command.UpdateAdminCommand;
import useCase.admin.command.UpdatePasswordCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;

public class AdminService implements
        AddAdminUseCase,
        GetAllAdminsUseCase,
        GetByIdAdminUseCase,
        UpdateAdminUseCase,
        UpdatePasswordUseCase,
        DeleteAdminUseCase {
    private final SimpleDateFormat formatter;
    private final UserRepository<Admin> adminRepository;


    public AdminService(SimpleDateFormat formatter, UserRepository<Admin> adminRepository) {
        this.formatter = formatter;
        this.adminRepository = adminRepository;
    }

    @Override
    public void addAdmin(AddAdminCommand command) throws ParseException {
        Optional<Admin> adminOptional = adminRepository.findByUserName(command.username);
        // TODO: Implement checking if other type of users have same username
        if (adminOptional.isPresent())
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

        Optional<Admin> adminOptional = adminRepository.findByUserName(command.username);
        if (adminOptional.isPresent() && !adminOptional.get().getUsername().equals(admin.getUsername()))
            throw new UserNameTakenException(command.username);

        admin.setName(command.name);
        admin.setSurname(command.surname);
        admin.setUsername(command.username);
        admin.setDateOfBirth(formatter.parse(command.dateOfBirth));
        admin.setGender(Gender.valueOf(command.gender));

        adminRepository.save(admin);
    }

    @Override
    public void updatePassword(UpdatePasswordCommand command) {
        Admin admin = adminRepository.findByIdAndArchivedFalse(command.id)
                .orElseThrow(() -> new AdminNotFoundException(command.id));

        admin.setPassword(command.password);

        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new AdminNotFoundException(id));

        admin.archive();

        adminRepository.save(admin);
    }
}
