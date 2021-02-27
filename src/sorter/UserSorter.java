package sorter;

import model.User;

import java.util.Comparator;
import java.util.List;

public class UserSorter {
    public static void sortByName(List<User> users, int order) {
        Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return order * o1.getName().compareTo(o2.getName());
            }
        };

        users.sort(userComparator);
    }
}
