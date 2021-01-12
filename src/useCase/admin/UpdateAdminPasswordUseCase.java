package useCase.admin;

import useCase.admin.command.UpdateAdminPasswordCommand;

public interface UpdateAdminPasswordUseCase {
    void updatePassword(UpdateAdminPasswordCommand command);
}
