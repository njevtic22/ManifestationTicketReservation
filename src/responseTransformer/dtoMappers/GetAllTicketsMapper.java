package responseTransformer.dtoMappers;

import model.Ticket;
import useCase.ticket.dto.GetAllTicketsDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllTicketsMapper implements DTOMapper<Ticket, GetAllTicketsDTO>  {
    private final SimpleDateFormat formatter;

    public GetAllTicketsMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllTicketsDTO toDTO(Ticket ticket) {
        return new GetAllTicketsDTO(ticket, formatter.format(ticket.getManifestation().getHoldingDate()));
    }

    @Override
    public List<GetAllTicketsDTO> toDTOList(Collection<Ticket> tickets) {
        return tickets
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
