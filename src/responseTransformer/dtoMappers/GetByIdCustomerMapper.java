package responseTransformer.dtoMappers;

import model.Customer;
import useCase.customer.dto.GetByIdCustomerDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetByIdCustomerMapper implements DTOMapper<Customer, GetByIdCustomerDTO> {
    private final SimpleDateFormat formatter;

    public GetByIdCustomerMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetByIdCustomerDTO toDTO(Customer customer) {
        return new GetByIdCustomerDTO(customer, formatter.format(customer.getDateOfBirth()));
    }

    @Override
    public List<GetByIdCustomerDTO> toDTOList(Collection<Customer> customers) {
        return customers
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
