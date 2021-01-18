package responseTransformer.dtoMappers;

import model.Manifestation;
import useCase.manifestation.dto.GetAllManifestationsDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllManifestationsMapper implements DTOMapper<Manifestation, GetAllManifestationsDTO> {
    private final SimpleDateFormat formatter;

    public GetAllManifestationsMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetAllManifestationsDTO toDTO(Manifestation manifestation) {
        return new GetAllManifestationsDTO(manifestation, formatter.format(manifestation.getHoldingDate()));
    }

    @Override
    public List<GetAllManifestationsDTO> toDTOList(Collection<Manifestation> manifestations) {
        return manifestations
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
