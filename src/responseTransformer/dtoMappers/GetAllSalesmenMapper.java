package responseTransformer.dtoMappers;

import model.Salesman;
import useCase.salesman.dto.GetAllSalesmenDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllSalesmenMapper implements DTOMapper<Salesman, GetAllSalesmenDTO> {
    private final SimpleDateFormat formatter;

    public GetAllSalesmenMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllSalesmenDTO toDTO(Salesman salesman) {
        return new GetAllSalesmenDTO(salesman, formatter.format(salesman.getDateOfBirth()));
    }

    @Override
    public List<GetAllSalesmenDTO> toDTOList(Collection<Salesman> salesmen) {
        return salesmen
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
