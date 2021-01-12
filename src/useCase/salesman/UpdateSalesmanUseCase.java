package useCase.salesman;

import useCase.salesman.command.UpdateSalesmanCommand;

import java.text.ParseException;

public interface UpdateSalesmanUseCase {
    void updateSalesman(UpdateSalesmanCommand command) throws ParseException;
}
