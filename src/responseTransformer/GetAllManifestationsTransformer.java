package responseTransformer;

import com.google.gson.Gson;
import model.Manifestation;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.manifestation.dto.GetAllManifestationsDTO;

import java.util.Collection;

public class GetAllManifestationsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Manifestation, GetAllManifestationsDTO> mapper;

    public GetAllManifestationsTransformer(Gson gson, DTOMapper<Manifestation, GetAllManifestationsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        Collection<Manifestation> manifestations = (Collection<Manifestation>) o;
        return gson.toJson(mapper.toDTOList(manifestations));
    }
}
