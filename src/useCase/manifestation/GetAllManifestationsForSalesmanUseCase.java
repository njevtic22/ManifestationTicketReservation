package useCase.manifestation;

import model.Manifestation;
import model.Salesman;

import java.util.Collection;

public interface GetAllManifestationsForSalesmanUseCase {
    Collection<Manifestation> getAllManifestationsForSalesman(Salesman salesman);
}
