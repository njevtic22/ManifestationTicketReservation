package responseTransformer;

import com.google.gson.Gson;
import model.Ticket;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.ticket.dto.GetAllTicketsDTO;

import java.util.Collection;

public class GetAllTicketsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Ticket, GetAllTicketsDTO> mapper;

    public GetAllTicketsTransformer(Gson gson, DTOMapper<Ticket, GetAllTicketsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Ticket> tickets = (Collection<Ticket>) o;
        return gson.toJson(mapper.toDTOList(tickets));
    }
}
