package sorter;

import model.Ticket;

import java.util.Comparator;
import java.util.List;

public class TicketSorter {
    public void sortById(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return order * o1.getAppId().toLowerCase().compareTo(o2.getAppId().toLowerCase());
            }
        };

        tickets.sort(ticketComparator);
    }

    public void sortByPrice(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return order * Double.compare(o1.getPrice(), o2.getPrice());
            }
        };

        tickets.sort(ticketComparator);
    }

    public void sortByStatus(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return order * o1.getStatus().compareTo(o2.getStatus());
            }
        };

        tickets.sort(ticketComparator);
    }

    public void sortByType(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return order * o1.getType().compareTo(o2.getType());
            }
        };

        tickets.sort(ticketComparator);
    }

    public void sortByManifestation(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return order * o1.getManifestation().getName().toLowerCase().compareTo(o2.getManifestation().getName().toLowerCase());
            }
        };

        tickets.sort(ticketComparator);
    }

    public void sortByCustomer(List<Ticket> tickets, int order) {
        Comparator<Ticket> ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                if (o1.getCustomer() == null) {
                    if (o2.getCustomer() == null) {
                        return order * 0;
                    } else {
                        return order * 1;
                    }
                } else {
                    if (o2.getCustomer() == null) {
                        return order * (-1);
                    } else {

                        String fullName1 = o1.getCustomer().getName() + " " + o1.getCustomer().getSurname();
                        String fullName2 = o2.getCustomer().getName() + " " + o2.getCustomer().getSurname();
                        return order * fullName1.compareToIgnoreCase(fullName2);
                    }
                }
            }
        };

        tickets.sort(ticketComparator);
    }
}
