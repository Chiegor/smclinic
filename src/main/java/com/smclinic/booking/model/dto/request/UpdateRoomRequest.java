package com.smclinic.booking.model.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateRoomRequest(
        @Min(value = 1, message = "Seats must be at least 1")
        @Max(value = 20, message = "Seats cannot exceed 20")
        Integer seats
) {
    @AssertTrue(message = "At least one field must be provided for update")
    public boolean isAnyFieldPresent() {
        return seats != null;
    }
}
