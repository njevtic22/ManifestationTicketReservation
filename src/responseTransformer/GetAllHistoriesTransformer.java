package responseTransformer;

import com.google.gson.Gson;
import model.WithdrawalHistory;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.withdrawalHistory.dto.GetAllHistoriesDTO;
import utility.PaginatedResponse;

import java.util.Collection;
import java.util.List;

public class GetAllHistoriesTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<WithdrawalHistory, GetAllHistoriesDTO> mapper;

    public GetAllHistoriesTransformer(Gson gson, DTOMapper<WithdrawalHistory, GetAllHistoriesDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<WithdrawalHistory> paginatedResponse = (PaginatedResponse<WithdrawalHistory>) o;
        List<GetAllHistoriesDTO> historiesDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        historiesDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
