package utility;

import java.util.List;

public class Pagination {
    public static <T> List<T> paginate(List<T> elements, int page, int size) {
        if (page < 0 || size < 1)
            return List.of();

        /*
         *
         * @throws IndexOutOfBoundsException if an endpoint index value is out of range
         *         {@code (fromIndex < 0 || toIndex > size)}
         * @throws IllegalArgumentException if the endpoint indices are out of order
         *         {@code (fromIndex > toIndex)}
         *
         * arrUsers.subList(fromIndex, toIndex);
         *
         * */

        int from = Math.max(page * size, 0);
        int to = Math.min((page + 1) * size, elements.size());

        if (from > to)
            return List.of();

        return elements.subList(from, to);
    }
}
