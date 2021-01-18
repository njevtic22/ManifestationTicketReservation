package useCase.manifestation;

import model.Manifestation;
import model.User;

import java.util.Collection;

public interface GetAllManifestationsUseCase {
    Collection<Manifestation> getAllManifestations(User user);
}
