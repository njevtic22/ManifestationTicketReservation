package responseTransformer;

import com.google.gson.Gson;
import model.Customer;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.customer.dto.GetAllCustomersDTO;
import utility.PaginatedResponse;

import java.util.Collection;
import java.util.List;

public class GetAllCustomersTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Customer, GetAllCustomersDTO> mapper;

    public GetAllCustomersTransformer(Gson gson, DTOMapper<Customer, GetAllCustomersDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<Customer> paginatedResponse = (PaginatedResponse<Customer>) o;
        List<GetAllCustomersDTO> customersDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        customersDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
