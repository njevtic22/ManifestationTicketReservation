package utility;

import java.util.List;

public class PaginatedResponse<T> {
    public final List<T> data;
    public final long totalNumberOfResults;
    public final boolean hasPreviousPage;
    public final boolean hasNextPage;

    public PaginatedResponse(List<T> data, long totalNumberOfResults, boolean hasPreviousPage, boolean hasNextPage) {
        this.data = data;
        this.totalNumberOfResults = totalNumberOfResults;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
    }
}
