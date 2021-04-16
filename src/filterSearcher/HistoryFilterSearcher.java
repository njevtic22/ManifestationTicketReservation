package filterSearcher;

import model.ManifestationStatus;
import model.ManifestationType;
import model.TicketType;
import model.WithdrawalHistory;

import java.util.Collection;
import java.util.Date;

public class HistoryFilterSearcher {
    public void filterByTicketType(TicketType type, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getType() != type);
    }

    public void filterByManifestationStatus(ManifestationStatus status, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getManifestation().getStatus() != status);
    }

    public void filterByManifestationType(ManifestationType type, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getManifestation().getType() != type);
    }

    public void searchByHistoryDateFrom(Date searchHistoryDateFrom, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getWithdrawalDate().before(searchHistoryDateFrom));
    }

    public void searchByHistoryDateTo(Date searchHistoryDateTo, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getWithdrawalDate().after(searchHistoryDateTo));
    }

    public void searchByTicketPriceFrom(long priceFrom, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> priceFrom > history.getPrice());
    }

    public void searchByTicketPriceTo(long priceTo, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getPrice() > priceTo);
    }

    public void searchByManifestationName(String manifestationName, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> !history.getManifestation().getName().toLowerCase().contains(manifestationName.toLowerCase()));
    }

    public void searchByManifestationDateFrom(Date manifestationDateFrom, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getManifestation().getHoldingDate().before(manifestationDateFrom));
    }

    public void searchByManifestationDateTo(Date manifestationDateTo, Collection<WithdrawalHistory> histories) {
        histories.removeIf(history -> history.getManifestation().getHoldingDate().after(manifestationDateTo));
    }
}
