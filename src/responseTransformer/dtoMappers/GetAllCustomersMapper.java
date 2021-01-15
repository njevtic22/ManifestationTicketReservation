package responseTransformer.dtoMappers;

import model.Customer;
import useCase.customer.dto.GetAllCustomersDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllCustomersMapper implements DTOMapper<Customer, GetAllCustomersDTO> {
    private final SimpleDateFormat formatter;

    public GetAllCustomersMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllCustomersDTO toDTO(Customer customer) {
        return new GetAllCustomersDTO(customer, formatter.format(customer.getDateOfBirth()));
    }

    @Override
    public List<GetAllCustomersDTO> toDTOList(Collection<Customer> customers) {
        return customers
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
