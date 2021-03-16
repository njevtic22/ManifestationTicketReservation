package responseTransformer;

import com.google.gson.Gson;
import model.User;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.user.dto.GetAllUsersDTO;
import utility.PaginatedResponse;

import java.util.List;

public class GetAllUsersTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<User, GetAllUsersDTO> mapper;

    public GetAllUsersTransformer(Gson gson, DTOMapper<User, GetAllUsersDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<User> paginatedResponse = (PaginatedResponse<User>) o;
        List<GetAllUsersDTO> usersDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        usersDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
