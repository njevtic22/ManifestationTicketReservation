package useCase.salesman;

import useCase.salesman.command.AddSalesmanCommand;

import java.text.ParseException;

public interface AddSalesmanUseCase {
    void addSalesman(AddSalesmanCommand command) throws ParseException;
}
