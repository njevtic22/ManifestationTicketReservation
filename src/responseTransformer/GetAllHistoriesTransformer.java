package responseTransformer;

import com.google.gson.Gson;
import model.WithdrawalHistory;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.withdrawalHistory.dto.GetAllHistoriesDTO;

import java.util.Collection;

public class GetAllHistoriesTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<WithdrawalHistory, GetAllHistoriesDTO> mapper;

    public GetAllHistoriesTransformer(Gson gson, DTOMapper<WithdrawalHistory, GetAllHistoriesDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<WithdrawalHistory> histories = (Collection<WithdrawalHistory>) o;
        return gson.toJson(mapper.toDTOList(histories));
    }
}
