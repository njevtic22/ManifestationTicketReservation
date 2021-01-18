package useCase.withdrawalHistory;

import model.Customer;
import model.WithdrawalHistory;

import java.util.Collection;

public interface GetHistoriesUseCase {
    Collection<WithdrawalHistory> getHistories(Customer customer);
}
