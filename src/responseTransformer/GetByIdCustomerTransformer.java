package responseTransformer;

import com.google.gson.Gson;
import model.Customer;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.customer.dto.GetByIdCustomerDTO;

public class GetByIdCustomerTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Customer, GetByIdCustomerDTO> mapper;

    public GetByIdCustomerTransformer(Gson gson, DTOMapper<Customer, GetByIdCustomerDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Customer customer = (Customer) o;
        return gson.toJson(mapper.toDTO(customer));
    }
}
