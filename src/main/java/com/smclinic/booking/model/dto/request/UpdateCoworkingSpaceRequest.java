package com.smclinic.booking.model.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public record UpdateCoworkingSpaceRequest(
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,
        @Size(max = 200, message = "Address must be less than 200 characters")
        String address
) {
    @AssertTrue(message = "At least one field must be provided for update")
    public boolean isAnyFieldPresent() {
        return name != null || address != null;
    }
}
