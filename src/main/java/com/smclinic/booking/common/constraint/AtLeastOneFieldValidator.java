package com.smclinic.booking.common.constraint;

import com.smclinic.booking.model.dto.request.UpdateCoworkingSpaceRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, UpdateCoworkingSpaceRequest> {
    @Override
    public boolean isValid(UpdateCoworkingSpaceRequest request, ConstraintValidatorContext context) {
        return request.name() != null || request.address() != null;
    }
}
