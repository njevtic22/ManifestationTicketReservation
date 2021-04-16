package sorter;

import model.WithdrawalHistory;

import java.util.Comparator;
import java.util.List;

public class HistorySorter {
    public void sortByTicketAppId(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getTicketId().compareToIgnoreCase(o2.getTicketId());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByWithdrawalDate(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getWithdrawalDate().compareTo(o2.getWithdrawalDate());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByPrice(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * Double.compare(o1.getPrice(), o2.getPrice());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByTicketType(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getType().compareTo(o2.getType());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByManifestation(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getManifestation().getName().compareToIgnoreCase(o2.getManifestation().getName());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByManifestationStatus(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getManifestation().getStatus().compareTo(o2.getManifestation().getStatus());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByManifestationType(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getManifestation().getType().compareTo(o2.getManifestation().getType());
            }
        };

        histories.sort(historyComparator);
    }

    public void sortByManifestationDate(List<WithdrawalHistory> histories, int order) {
        Comparator<WithdrawalHistory> historyComparator = new Comparator<WithdrawalHistory>() {
            @Override
            public int compare(WithdrawalHistory o1, WithdrawalHistory o2) {
                return order * o1.getManifestation().getHoldingDate().compareTo(o2.getManifestation().getHoldingDate());
            }
        };

        histories.sort(historyComparator);
    }
}
