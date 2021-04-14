package useCase.manifestation;

import useCase.manifestation.command.UpdateManifestationCommand;

import java.text.ParseException;

public interface UpdateManifestationUseCase {
    void updateManifestation(UpdateManifestationCommand command) throws ParseException, CloneNotSupportedException;
}
