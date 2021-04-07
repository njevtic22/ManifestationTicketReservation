package useCase.manifestation.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class AddManifestationCommand implements SelfValidating {
    public Long salesmanId;
    public String name;
    public long maxNumberOfTickets;
    public double regularTicketPrice;
    public String holdingDate;
    public String description;
    public String status;
    public String type;

    public double longitude;
    public double latitude;

    public String street;
    public long number;
    public String city;
    public String postalCode;

    public String imageBase64;
    public String imageType;

    public AddManifestationCommand(
            Long salesmanId,
            String name,
            long maxNumberOfTickets,
            double regularTicketPrice,
            String holdingDate,
            String description,
            String status,
            String type,
            double longitude,
            double latitude,
            String street,
            long number,
            String city,
            String postalCode,
            String imageBase64,
            String imageType
    ) {
        this.salesmanId = salesmanId;
        this.name = name;
        this.maxNumberOfTickets = maxNumberOfTickets;
        this.regularTicketPrice = regularTicketPrice;
        this.holdingDate = holdingDate;
        this.description = description;
        this.status = status;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.imageBase64 = imageBase64;
        this.imageType = imageType;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (salesmanId == null || salesmanId <= 0)
            throw new ConstraintViolationException("Salesman id must be positive number.");

        if (name == null || name.trim().isEmpty())
            throw new ConstraintViolationException("Name must not be empty.");

        if (maxNumberOfTickets < 0)
            throw new ConstraintViolationException("Max number of tickets must not be negative number.");

        if (regularTicketPrice <= 0)
            throw new ConstraintViolationException("Regular ticket price must not be empty.");

        if (holdingDate == null || holdingDate.trim().isEmpty())
            throw new ConstraintViolationException("Holding date must not be empty.");

        if (description == null || description.trim().isEmpty())
            throw new ConstraintViolationException("Description must not be empty.");

        if (status == null || status.trim().isEmpty())
            throw new ConstraintViolationException("Manifestation status must not be empty.");

        if (type == null || type.trim().isEmpty())
            throw new ConstraintViolationException("Manifestation type must not be empty.");

        if (!(-180 <= longitude && longitude <= 180))
            throw new ConstraintViolationException("Longitude must be between -180 and 180.");

        if (!(-90 <= latitude && latitude <= 90))
            throw new ConstraintViolationException("Latitude must be between -90 and 90.");

        if (street == null || street.trim().isEmpty())
            throw new ConstraintViolationException("Street must not be empty.");

        if (number <= 0)
            throw new ConstraintViolationException("Street number must be positive.");

        if (city == null || city.trim().isEmpty())
            throw new ConstraintViolationException("City name must not be empty.");

        if (postalCode == null || postalCode.trim().isEmpty())
            throw new ConstraintViolationException("Postal code must not be empty.");

        if (imageBase64 == null /*|| imageBase64.trim().isEmpty()*/)
            throw new ConstraintViolationException("Image location must not be empty.");

        if (imageType == null /*|| imageType.trim().isEmpty()*/)
            throw new ConstraintViolationException("Image type must not be empty.");
    }
}
