package useCase.admin;

import useCase.admin.command.UpdateAdminCommand;

import java.text.ParseException;

public interface UpdateAdminUseCase {
    void updateAdmin(UpdateAdminCommand command) throws ParseException;
}
