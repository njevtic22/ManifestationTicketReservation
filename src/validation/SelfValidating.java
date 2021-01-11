package validation;

import exception.ConstraintViolationException;

public interface SelfValidating {
    void validateSelf();

    static Long validId(String strId) {
        Long validId = 0L;
        try {
            validId = Long.valueOf(strId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ConstraintViolationException("Id must be positive number.", e);
        }

        if (validId <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        return validId;
    }

    static Long validId(Long id) {
        if (id <= 0)
            throw new ConstraintViolationException("Id must be positive number.");

        return id;
    }
}
