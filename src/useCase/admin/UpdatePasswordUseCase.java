package useCase.admin;

import useCase.admin.command.UpdatePasswordCommand;

public interface UpdatePasswordUseCase {
    void updatePassword(UpdatePasswordCommand command);
}
