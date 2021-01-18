package useCase.manifestation;

import useCase.manifestation.command.AddManifestationCommand;

import java.text.ParseException;

public interface AddManifestationUseCase {
    void addManifestation(AddManifestationCommand command) throws ParseException;
}
