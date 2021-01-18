package useCase.manifestation;

import useCase.manifestation.command.UpdateLocationCommand;

public interface UpdateLocationUseCase {
    void updateLocation(UpdateLocationCommand command);
}
