package responseTransformer;

import com.google.gson.Gson;
import model.Admin;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.admin.dto.GetByIdAdminDTO;

import java.util.Collection;

public class GetByIdAdminTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Admin, GetByIdAdminDTO> mapper;

    public GetByIdAdminTransformer(Gson gson, DTOMapper<Admin, GetByIdAdminDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Admin admin = (Admin) o;
        return gson.toJson(mapper.toDTO(admin));
    }
}
