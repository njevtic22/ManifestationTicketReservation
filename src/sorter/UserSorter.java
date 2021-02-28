package sorter;

import model.Customer;
import model.User;

import java.util.Comparator;
import java.util.List;

public class UserSorter {
    public void sortByName(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getName().compareTo(o2.getName());
            }
        };

        users.sort(userComparator);
    }

    public void sortBySurname(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getSurname().compareTo(o2.getSurname());
            }
        };

        users.sort(userComparator);
    }

    public void sortByUsername(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getUsername().compareTo(o2.getUsername());
            }
        };

        users.sort(userComparator);
    }

    public void sortByDateOfBirth(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
            }
        };

        users.sort(userComparator);
    }

    public void sortByGender(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getGender().compareTo(o2.getGender());
            }
        };

        users.sort(userComparator);
    }

    public void sortByRole(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
            }
        };

        users.sort(userComparator);
    }

    public void sortByType(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (!(o1 instanceof Customer))
                    return -1 * order;
                if (!(o2 instanceof Customer))
                    return order;

                Customer c1 = (Customer) o1;
                Customer c2 = (Customer) o2;

                return order * c1.getType().compareTo(c2.getType());
            }
        };

        users.sort(userComparator);
    }

    public void sortByPoints(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (!(o1 instanceof Customer))
                    return -1 * order;
                if (!(o2 instanceof Customer))
                    return order;

                Customer c1 = (Customer) o1;
                Customer c2 = (Customer) o2;

                return order * Double.compare(c1.getPoints(), c2.getPoints());
            }
        };

        users.sort(userComparator);
    }
}
