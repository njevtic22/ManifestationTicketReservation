package responseTransformer.dtoMappers;

import model.WithdrawalHistory;
import useCase.withdrawalHistory.dto.GetAllHistoriesDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllHistoriesMapper implements DTOMapper<WithdrawalHistory, GetAllHistoriesDTO> {
    private final SimpleDateFormat formatter;

    public GetAllHistoriesMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllHistoriesDTO toDTO(WithdrawalHistory history) {
        return new GetAllHistoriesDTO(history, formatter.format(history.getWithdrawalDate()));
    }

    @Override
    public List<GetAllHistoriesDTO> toDTOList(Collection<WithdrawalHistory> histories) {
        return histories
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
