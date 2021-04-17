package sorter;

import model.Customer;

import java.util.Comparator;
import java.util.List;

public class CustomerSorter {
    public void sortByName(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortBySurname(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getSurname().toLowerCase().compareTo(o2.getSurname().toLowerCase());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByUsername(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByDateOfBirth(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByGender(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getGender().compareTo(o2.getGender());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByRole(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByType(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * o1.getType().compareTo(o2.getType());
            }
        };

        customers.sort(customerComparator);
    }

    public void sortByPoints(List<Customer> customers, int order) {
        Comparator<Customer> customerComparator = new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return order * Double.compare(o1.getPoints(), o2.getPoints());
            }
        };

        customers.sort(customerComparator);
    }
}
