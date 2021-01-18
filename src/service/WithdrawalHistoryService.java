package service;

import model.Customer;
import model.WithdrawalHistory;
import repository.Repository;
import useCase.withdrawalHistory.GetHistoriesUseCase;

import java.util.Collection;

public class WithdrawalHistoryService implements
        GetHistoriesUseCase {
    private final Repository<WithdrawalHistory, Long> historyRepository;

    public WithdrawalHistoryService(Repository<WithdrawalHistory, Long> historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public Collection<WithdrawalHistory> getHistories(Customer customer) {
        return customer.getWithdrawalHistory();
    }
}
