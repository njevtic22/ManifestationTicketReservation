package responseTransformer;

import com.google.gson.Gson;
import model.Ticket;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.ticket.dto.GetAllTicketsDTO;
import utility.PaginatedResponse;

import java.util.Collection;
import java.util.List;

public class GetAllTicketsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Ticket, GetAllTicketsDTO> mapper;

    public GetAllTicketsTransformer(Gson gson, DTOMapper<Ticket, GetAllTicketsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<Ticket> paginatedResponse = (PaginatedResponse<Ticket>) o;
        List<GetAllTicketsDTO> ticketsDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        ticketsDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
