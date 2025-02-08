package com.smclinic.booking.model.dto.request;

import com.smclinic.booking.common.constraint.AtLeastOneField;
import jakarta.validation.constraints.Size;

@AtLeastOneField
public record UpdateCoworkingSpaceRequest(
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,
        @Size(max = 200, message = "Address must be less than 200 characters")
        String address
) {
}
