package useCase.authentication;

import model.User;

public interface GetUserFromTokenAuthenticationCase {
    User getUserFromToken(String jwt);
}
