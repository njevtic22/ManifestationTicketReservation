package responseTransformer;

import com.google.gson.Gson;
import model.Customer;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.customer.dto.GetAllCustomersDTO;

import java.util.Collection;

public class GetAllCustomersTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Customer, GetAllCustomersDTO> mapper;

    public GetAllCustomersTransformer(Gson gson, DTOMapper<Customer, GetAllCustomersDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Customer> customers = (Collection<Customer>) o;
        return gson.toJson(mapper.toDTOList(customers));
    }
}
