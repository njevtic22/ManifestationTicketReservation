package service;

import model.Manifestation;
import model.WithdrawalHistory;
import repository.Repository;
import useCase.withdrawalHistory.AddHistoryUseCase;
import useCase.withdrawalHistory.GetHistoriesUseCase;
import useCase.withdrawalHistory.command.AddHistoryCommand;

import java.text.SimpleDateFormat;

public class WithdrawalHistoryService implements
        AddHistoryUseCase,
        GetHistoriesUseCase {
    private final SimpleDateFormat formatter;
    private final Repository<WithdrawalHistory, Long> historyRepository;
    private final Repository<Manifestation, Long> manifestationRepository;

    public WithdrawalHistoryService(SimpleDateFormat formatter, Repository<WithdrawalHistory, Long> historyRepository, Repository<Manifestation, Long> manifestationRepository) {
        this.formatter = formatter;
        this.historyRepository = historyRepository;
        this.manifestationRepository = manifestationRepository;
    }

    @Override
    public void addHistory(AddHistoryCommand command) {

    }

    @Override
    public void getHistories(Long customerId) {

    }
}
