package service;

import model.Admin;
import model.Gender;
import repository.Repository;
import useCase.admin.AddAdminUseCase;
import useCase.admin.GetAllAdminsUseCase;
import useCase.admin.command.AddAdminCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class AdminService implements
        AddAdminUseCase,
        GetAllAdminsUseCase {
    private final SimpleDateFormat formatter;
    private final Repository<Admin, Long> adminRepository;


    public AdminService(SimpleDateFormat formatter, Repository<Admin, Long> adminRepository) {
        this.formatter = formatter;
        this.adminRepository = adminRepository;
    }

    @Override
    public void addAdmin(AddAdminCommand command) throws ParseException {
        Admin admin = new Admin(
                command.id,
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
        return adminRepository.findAll();
    }
}
