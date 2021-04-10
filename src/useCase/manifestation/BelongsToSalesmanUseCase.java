package useCase.manifestation;

import model.Salesman;

public interface BelongsToSalesmanUseCase {
    boolean belongsToSalesman(Long manifestationId, Salesman salesman);
}
