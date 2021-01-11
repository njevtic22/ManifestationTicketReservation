package service;

import exception.UserNameTakenException;
import model.Gender;
import model.Salesman;
import repository.UserRepository;
import useCase.salesman.AddSalesmanUseCase;
import useCase.salesman.command.AddSalesmanCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class SalesmanService implements
        AddSalesmanUseCase {
    private final SimpleDateFormat formatter;
    private final UserRepository<Salesman> salesmanRepository;

    public SalesmanService(SimpleDateFormat formatter, UserRepository<Salesman> salesmanRepository) {
        this.formatter = formatter;
        this.salesmanRepository = salesmanRepository;
    }

    @Override
    public void addSalesman(AddSalesmanCommand command) throws ParseException {
        Optional<Salesman> salesmanOptional = salesmanRepository.findByUserName(command.username);
        // TODO: Implement checking if other type of users have same username
        if (salesmanOptional.isPresent())
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
        salesmanRepository.save(salesman);
    }
}
