package useCase.manifestation.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateManifestationCommand implements SelfValidating {
    public Long id;
    public String name;
    public double regularTicketPrice;
    public String holdingDate;
    public String description;
    public String type;

    public String imageBase64;
    public String imageType;

    public UpdateManifestationCommand(
            Long id,
            String name,
            double regularTicketPrice,
            String holdingDate,
            String description,
            String type,
            String imageBase64,
            String imageType
    ) {
        this.id = id;
        this.name = name;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.description = description;
        this.type = type;

        this.imageBase64 = imageBase64;
        this.imageType = imageType;
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

        if (description == null || description.trim().isEmpty())
            throw new ConstraintViolationException("Description must not be empty.");

        if (type == null || type.trim().isEmpty())
            throw new ConstraintViolationException("Manifestation type must not be empty.");

        if (imageBase64 == null /*|| imageBase64.trim().isEmpty()*/)
            throw new ConstraintViolationException("Image location must not be empty.");

        if (imageType == null /*|| imageType.trim().isEmpty()*/)
            throw new ConstraintViolationException("Image type must not be empty.");
    }
}
