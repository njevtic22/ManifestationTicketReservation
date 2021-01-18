package responseTransformer;

import com.google.gson.Gson;
import model.Manifestation;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.manifestation.dto.GetByIdManifestationDTO;

public class GetByIdManifestationTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Manifestation, GetByIdManifestationDTO> mapper;

    public GetByIdManifestationTransformer(Gson gson, DTOMapper<Manifestation, GetByIdManifestationDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Manifestation manifestation = (Manifestation) o;
        return gson.toJson(mapper.toDTO(manifestation));
    }
}
