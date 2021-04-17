package controller;

import com.google.gson.Gson;
import filterSearcher.HistoryFilterSearcher;
import model.Customer;
import model.ManifestationStatus;
import model.ManifestationType;
import model.TicketType;
import model.WithdrawalHistory;
import org.eclipse.jetty.http.HttpStatus;
import responseTransformer.GetAllHistoriesTransformer;
import responseTransformer.dtoMappers.GetAllHistoriesMapper;
import sorter.HistorySorter;
import spark.Request;
import spark.Response;
import spark.Route;
import useCase.withdrawalHistory.GetHistoriesUseCase;
import utility.PaginatedResponse;
import utility.Pagination;
import utility.RoleEnsure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.path;

public class WithdrawalHistoryController {
    private Gson gson;
    private final SimpleDateFormat formatter;
    private GetHistoriesUseCase getHistoriesUseCase;
    private final HistoryFilterSearcher historyFilterSearcher;
    private final HistorySorter historySorter;
    private Pagination pagination;

    private RoleEnsure ensureUserIsAdmin = AuthenticationController::ensureUserIsAdmin;
    private RoleEnsure ensureUserIsSalesman = AuthenticationController::ensureUserIsSalesman;
    private RoleEnsure ensureUserIsCustomer = AuthenticationController::ensureUserIsCustomer;
    private RoleEnsure ensureUserIsAdminOrSalesman = AuthenticationController::ensureUserIsAdminOrSalesman;
    private RoleEnsure ensureUserIsAdminOrCustomer = AuthenticationController::ensureUserIsAdminOrCustomer;

    public WithdrawalHistoryController(
            Gson gson,
            SimpleDateFormat formatter,
            GetHistoriesUseCase getHistoriesUseCase,
            HistoryFilterSearcher historyFilterSearcher,
            HistorySorter historySorter,
            Pagination pagination
    ) {
        this.gson = gson;
        this.formatter = formatter;
        this.getHistoriesUseCase = getHistoriesUseCase;
        this.historyFilterSearcher = historyFilterSearcher;
        this.historySorter = historySorter;
        this.pagination = pagination;
        this.setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/histories", () -> {
                get("", getAll, new GetAllHistoriesTransformer(gson, new GetAllHistoriesMapper(formatter)));
            });
        });
    }

    public Route getAll = (Request request, Response response) -> {
        ensureUserIsCustomer.ensure(request);

        Customer customer = request.attribute("user");
        ArrayList<WithdrawalHistory> histories = new ArrayList<>(getHistoriesUseCase.getHistories(customer));
        applyFilter(request, histories);
        applySearch(request, histories);
        applySort(request, histories);

        PaginatedResponse<WithdrawalHistory> paginatedHistories = pagination.doPagination(
                histories,
                request.queryParams("page"),
                request.queryParams("size")
        );


        response.status(HttpStatus.OK_200);
        return paginatedHistories;
    };

    private void applyFilter(Request request, Collection<WithdrawalHistory> histories) {
        if (request.queryParams("filterTicketType") != null)
            historyFilterSearcher.filterByTicketType(TicketType.valueOf(request.queryParams("filterTicketType")), histories);
        if (request.queryParams("filterManifestationStatus") != null)
            historyFilterSearcher.filterByManifestationStatus(ManifestationStatus.valueOf(request.queryParams("filterManifestationStatus")), histories);
        if (request.queryParams("filterManifestationType") != null)
            historyFilterSearcher.filterByManifestationType(ManifestationType.valueOf(request.queryParams("filterManifestationType")), histories);
    }

    private void applySearch(Request request, Collection<WithdrawalHistory> histories) throws ParseException {
        if (request.queryParams("searchHistoryDateFrom") != null)
            historyFilterSearcher.searchByHistoryDateFrom(formatter.parse(request.queryParams("searchHistoryDateFrom")), histories);
        if (request.queryParams("searchHistoryDateTo") != null)
            historyFilterSearcher.searchByHistoryDateTo(formatter.parse(request.queryParams("searchHistoryDateTo")), histories);
        if (request.queryParams("searchTicketPriceFrom") != null)
            historyFilterSearcher.searchByTicketPriceFrom(Long.parseLong(request.queryParams("searchTicketPriceFrom")), histories);
        if (request.queryParams("searchTicketPriceTo") != null)
            historyFilterSearcher.searchByTicketPriceTo(Long.parseLong(request.queryParams("searchTicketPriceTo")), histories);
        if (request.queryParams("searchManifestationName") != null)
            historyFilterSearcher.searchByManifestationName(request.queryParams("searchManifestationName"), histories);
        if (request.queryParams("searchManifestationDateFrom") != null)
            historyFilterSearcher.searchByManifestationDateFrom(formatter.parse(request.queryParams("searchManifestationDateFrom")), histories);
        if (request.queryParams("searchManifestationDateTo") != null)
            historyFilterSearcher.searchByManifestationDateTo(formatter.parse(request.queryParams("searchManifestationDateTo")), histories);
    }

    private void applySort(Request request, List<WithdrawalHistory> histories) {
        String sortBy = request.queryParams("sortBy");
        if (sortBy == null)
            return;
        sortBy = sortBy.toLowerCase();

        String sortOrderStr = request.queryParams("sortOrder");
        if (sortOrderStr == null)
            return;

        int sortOrder = sortOrderStr.equals("asc") ? 1 : -1;

        switch (sortBy) {
            case "ticketappid":
                historySorter.sortByTicketAppId(histories, sortOrder);
                break;
            case "historydate":
                historySorter.sortByWithdrawalDate(histories, sortOrder);
                break;
            case "price":
                historySorter.sortByPrice(histories, sortOrder);
                break;
            case "tickettype":
                historySorter.sortByTicketType(histories, sortOrder);
                break;
            case "manifestation":
                historySorter.sortByManifestation(histories, sortOrder);
                break;
            case "manifestationstatus":
                historySorter.sortByManifestationStatus(histories, sortOrder);
                break;
            case "manifestationtype":
                historySorter.sortByManifestationType(histories, sortOrder);
                break;
            case "manifestationdate":
                historySorter.sortByManifestationDate(histories, sortOrder);
                break;
        }
    }
}
