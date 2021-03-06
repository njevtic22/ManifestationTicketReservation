package useCase.authentication;

import useCase.authentication.command.ChangePasswordCommand;

public interface ChangePasswordAuthenticationCase {
    void changePassword(ChangePasswordCommand command);
}
