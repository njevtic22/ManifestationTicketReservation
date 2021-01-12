package responseTransformer.dtoMappers;

import model.Salesman;
import useCase.salesman.dto.GetByIdSalesmanDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetByIdSalesmanMapper implements DTOMapper<Salesman, GetByIdSalesmanDTO> {
    private final SimpleDateFormat formatter;

    public GetByIdSalesmanMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetByIdSalesmanDTO toDTO(Salesman salesman) {
        return new GetByIdSalesmanDTO(salesman, formatter.format(salesman.getDateOfBirth()));
    }

    @Override
    public List<GetByIdSalesmanDTO> toDTOList(Collection<Salesman> salesmen) {
        return salesmen
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
