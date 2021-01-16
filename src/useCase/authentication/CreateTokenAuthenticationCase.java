package useCase.authentication;

public interface CreateTokenAuthenticationCase {
    String createToken(String userName, String password);
}
