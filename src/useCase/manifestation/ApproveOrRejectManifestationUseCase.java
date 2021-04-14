package useCase.manifestation;

import useCase.manifestation.command.ApproveOrRejectCommand;

public interface ApproveOrRejectManifestationUseCase {
    void approveOrReject(ApproveOrRejectCommand command) throws CloneNotSupportedException;
}
