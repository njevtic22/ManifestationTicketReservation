package useCase.user;

import model.User;

import java.util.Collection;

public interface GetAllUsersUseCase {
    Collection<User> getAllUsers();
}
