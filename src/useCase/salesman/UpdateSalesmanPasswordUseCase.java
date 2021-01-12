package useCase.salesman;

import useCase.salesman.command.UpdateSalesmanPasswordCommand;

public interface UpdateSalesmanPasswordUseCase {
    void updatePassword(UpdateSalesmanPasswordCommand command);
}
