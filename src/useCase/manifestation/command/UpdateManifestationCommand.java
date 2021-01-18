package useCase.manifestation.command;

import exception.ConstraintViolationException;
import model.Salesman;
import validation.SelfValidating;

public class UpdateManifestationCommand implements SelfValidating {
    public Long id;
    public String name;
    //    public long numberOfTicketsLeft; = 0
    public double regularTicketPrice;
    public String holdingDate;
    public String status;
    public String type;

    public String imageLocation;

    public UpdateManifestationCommand(Long id, String name, double regularTicketPrice, String holdingDate, String status, String type, String imageLocation) {
        this.id = id;
        this.name = name;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.status = status;
        this.type = type;

        this.imageLocation = imageLocation;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (id == null || id <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");

        if (name == null || name.trim().isEmpty())
            throw new ConstraintViolationException("Name must not be empty.");

        if (regularTicketPrice <= 0)
            throw new ConstraintViolationException("Regular ticket price must be positive number.");

        if (holdingDate == null || holdingDate.trim().isEmpty())
            throw new ConstraintViolationException("Holding date must not be empty.");

        if (status == null || status.trim().isEmpty())
            throw new ConstraintViolationException("Manifestation status must not be empty.");

        if (type == null || type.trim().isEmpty())
            throw new ConstraintViolationException("Manifestation type must not be empty.");

        if (imageLocation == null || imageLocation.trim().isEmpty())
            throw new ConstraintViolationException("Image location must not be empty.");
    }
}