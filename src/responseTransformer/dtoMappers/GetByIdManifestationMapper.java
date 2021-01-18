package responseTransformer.dtoMappers;

import model.Manifestation;
import useCase.manifestation.dto.GetByIdManifestationDTO;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetByIdManifestationMapper implements DTOMapper<Manifestation, GetByIdManifestationDTO> {
    private final SimpleDateFormat formatter;

    public GetByIdManifestationMapper(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    @Override
    public GetByIdManifestationDTO toDTO(Manifestation manifestation) {
        return new GetByIdManifestationDTO(manifestation, formatter.format(manifestation.getHoldingDate()));
    }

    @Override
    public List<GetByIdManifestationDTO> toDTOList(Collection<Manifestation> manifestations) {
        return manifestations
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
