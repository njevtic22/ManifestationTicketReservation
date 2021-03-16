package responseTransformer;

import com.google.gson.Gson;
import model.Manifestation;
import responseTransformer.dtoMappers.DTOMapper;
import spark.ResponseTransformer;
import useCase.manifestation.dto.GetAllManifestationsDTO;
import utility.PaginatedResponse;

import java.util.List;

public class GetAllManifestationsTransformer implements ResponseTransformer {
    private final Gson gson;
    private final DTOMapper<Manifestation, GetAllManifestationsDTO> mapper;

    public GetAllManifestationsTransformer(Gson gson, DTOMapper<Manifestation, GetAllManifestationsDTO> mapper) {
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String render(Object o) throws Exception {
        PaginatedResponse<Manifestation> paginatedResponse = (PaginatedResponse<Manifestation>) o;
        List<GetAllManifestationsDTO> manifestationsDTO = mapper.toDTOList(paginatedResponse.data);

        return gson.toJson(
                new PaginatedResponse<>(
                        manifestationsDTO,
                        paginatedResponse.totalNumberOfResults,
                        paginatedResponse.hasPreviousPage,
                        paginatedResponse.hasNextPage
                )
        );
    }
}
