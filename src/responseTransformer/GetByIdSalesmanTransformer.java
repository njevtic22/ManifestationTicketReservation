package responseTransformer;

import com.google.gson.Gson;
import model.Salesman;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.salesman.dto.GetByIdSalesmanDTO;

public class GetByIdSalesmanTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Salesman, GetByIdSalesmanDTO> mapper;

    public GetByIdSalesmanTransformer(Gson gson, DTOMapper<Salesman, GetByIdSalesmanDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Salesman salesman = (Salesman) o;
        return gson.toJson(mapper.toDTO(salesman));
    }
}
