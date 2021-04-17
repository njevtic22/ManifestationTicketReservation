package utility;

import java.util.List;

public class Pagination {
    private int parsePage(String pageStr) {
        int page;
        try {
            page = Integer.parseInt(pageStr);
            if (page < 0)
                throw new NumberFormatException("Page can not be negative number.");

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid page argument.", e);
        }

        return page;
    }

    private int parseSize(String sizeStr) {
        int size;
        try {
            size = Integer.parseInt((sizeStr));
            if (size < 1)
                throw new NumberFormatException("Size can not be negative number.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid size argument.", e);
        }

        return size;
    }

    public <T> PaginatedResponse<T> doPagination(List<T> elements, String pageStr, String sizeStr) {
        int page = parsePage(pageStr);

        if (sizeStr.toLowerCase().equals("all"))
            return new PaginatedResponse<>(
                    elements,
                    elements.size(),
                    page > 0,
                    false
            );

        int size = parseSize(sizeStr);

        return doPagination(elements, page, size);
    }

    public <T> PaginatedResponse<T> doPagination(List<T> elements, int page, int size) {
        if (page < 0 || size < 1)
            throw new IllegalArgumentException("Invalid page and size arguments.");

        /*
         *
         * @throws IndexOutOfBoundsException if an endpoint index value is out of range
         *         {@code (fromIndex < 0 || toIndex > size)}
         * @throws IllegalArgumentException if the endpoint indices are out of order
         *         {@code (fromIndex > toIndex)}
         *
         * list.subList(fromIndex, toIndex);
         *
         * */

        int from = Math.max(page * size, 0);
        int to = Math.min((page + 1) * size, elements.size());

        if (from > to)
            return new PaginatedResponse<>(
                    List.of(),
                    elements.size(),
                    page > 0,
                    false
            );

        return new PaginatedResponse<>(
                elements.subList(from, to),
                elements.size(),
                page > 0,
                to < elements.size()
        );
    }
}
