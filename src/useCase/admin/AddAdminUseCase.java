package useCase.admin;

import useCase.admin.command.AddAdminCommand;

import java.text.ParseException;

public interface AddAdminUseCase {
    void addAdmin(AddAdminCommand command) throws ParseException;
}
