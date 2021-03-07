package useCase.manifestation;

import model.Manifestation;

import java.util.Collection;

public interface GetAllCreatedManifestationsUseCase {
    Collection<Manifestation> getCreatedManifestations();
}
