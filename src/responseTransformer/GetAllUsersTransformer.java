package responseTransformer;

import com.google.gson.Gson;
import model.User;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.user.dto.GetAllUsersDTO;

import java.util.Collection;

public class GetAllUsersTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<User, GetAllUsersDTO> mapper;

    public GetAllUsersTransformer(Gson gson, DTOMapper<User, GetAllUsersDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<User> users = (Collection<User>) o;
        return gson.toJson(mapper.toDTOList(users));
    }
}
