package useCase.withdrawalHistory;

import useCase.withdrawalHistory.command.AddHistoryCommand;

public interface AddHistoryUseCase {
    void addHistory(AddHistoryCommand command);
}
