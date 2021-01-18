package useCase.manifestation.command;

import exception.ConstraintViolationException;
import validation.SelfValidating;

public class UpdateLocationCommand implements SelfValidating {
    public Long manifestationId;

    public double longitude;
    public double latitude;

    public String street;
    public long number;
    public String city;
    public String postalCode;

    public UpdateLocationCommand(Long manifestationId, double longitude, double latitude, String street, long number, String city, String postalCode) {
        this.manifestationId = manifestationId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.validateSelf();
    }

    @Override
    public void validateSelf() {
        if (manifestationId == null || manifestationId <= 0)
            throw new ConstraintViolationException("Manifestation id must be positive number.");

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

    }
}
