package useCase.manifestation;

import model.Manifestation;

import java.util.Collection;

public interface GetAllManifestationsUseCase {
    Collection<Manifestation> getAllManifestations();
}
