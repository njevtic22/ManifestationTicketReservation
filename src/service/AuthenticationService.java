package service;

import exception.UserNameTakenException;
import model.Customer;
import model.CustomerType;
import model.Gender;
import model.Salesman;
import model.User;
import repository.AuthenticationRepository;
import security.PasswordEncoder;
import security.TokenUtils;
import useCase.authentication.CreateTokenAuthenticationCase;
import useCase.authentication.GetUserFromTokenAuthenticationCase;
import useCase.authentication.RegisterCustomerAuthenticationCase;
import useCase.authentication.RegisterSalesmanAuthenticationCase;
import useCase.authentication.command.RegisterUserCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class AuthenticationService implements
        CreateTokenAuthenticationCase,
        GetUserFromTokenAuthenticationCase,
        RegisterSalesmanAuthenticationCase,
        RegisterCustomerAuthenticationCase {
    private final SimpleDateFormat formatter;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationRepository authRepository;

    public AuthenticationService(SimpleDateFormat formatter, TokenUtils tokenUtils, PasswordEncoder passwordEncoder, AuthenticationRepository authRepository) {
        this.formatter = formatter;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
    }

    @Override
    public String createToken(String userName, String password) {
        User user = authRepository.findByUserNameAndArchivedFalse(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        return tokenUtils.generateToken(user.getId());
    }

    @Override
    public User getUserFromToken(String jwt) {
        Long id = tokenUtils.getIdFromToken(jwt);
        return authRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %d not found", id)));
    }

    @Override
    public void registerSalesman(RegisterUserCommand command) throws ParseException {
        if (isUsernameTaken(command.username))
            throw new UserNameTakenException(command.username);

        Salesman salesman = new Salesman(
                command.name,
                command.surname,
                command.username,
                command.password,
                formatter.parse(command.dateOfBirth),
                Gender.valueOf(command.gender),
                false
        );
        authRepository.registerSalesman(salesman);
    }

    @Override
    public Customer registerCustomer(RegisterUserCommand command) throws ParseException {
        if (isUsernameTaken(command.username))
            throw new UserNameTakenException(command.username);

        Customer customer = new Customer(
                command.name,
                command.surname,
                command.username,
                command.password,
                formatter.parse(command.dateOfBirth),
                Gender.valueOf(command.gender),
                false,
                0,
                CustomerType.BRONZE
        );
        authRepository.registerCustomer(customer);
        return customer;
    }

    private boolean isUsernameTaken(String usernameToValidate) {
        Optional<User> userOptional = authRepository.findByUserName(usernameToValidate);
        return userOptional.isPresent();
    }
}
