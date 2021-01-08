package responseTransformer;

import com.google.gson.Gson;
import model.Admin;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.admin.dto.GetAllAdminsDTO;

import java.util.Collection;

public class GetAllAdminsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Admin, GetAllAdminsDTO> mapper;

    public GetAllAdminsTransformer(Gson gson, DTOMapper<Admin, GetAllAdminsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Admin> admins = (Collection<Admin>) o;
        return gson.toJson(mapper.toDTOList(admins));
    }
}
