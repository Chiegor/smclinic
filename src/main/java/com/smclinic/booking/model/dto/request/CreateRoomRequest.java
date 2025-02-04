package com.smclinic.booking.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateRoomRequest(
        @NotNull(message = "Space ID is required") UUID spaceId,
        @Min(value = 1, message = "Seats must be at least 1")
        @Max(value = 20, message = "Seats cannot exceed 20")
        int seats
) {
    public int seats() {
        return seats;
    }
}
