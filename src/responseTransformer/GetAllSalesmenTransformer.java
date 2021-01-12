package responseTransformer;

import com.google.gson.Gson;
import model.Salesman;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.salesman.dto.GetAllSalesmenDTO;

import java.util.Collection;

public class GetAllSalesmenTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Salesman, GetAllSalesmenDTO> mapper;

    public GetAllSalesmenTransformer(Gson gson, DTOMapper<Salesman, GetAllSalesmenDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Salesman> salesmen = (Collection<Salesman>) o;
        return gson.toJson(mapper.toDTOList(salesmen));
    }
}
