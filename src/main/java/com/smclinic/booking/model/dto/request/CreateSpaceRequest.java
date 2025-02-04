package com.smclinic.booking.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSpaceRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,
        @NotBlank(message = "Address is required")
        @Size(max = 200, message = "Address must be less than 200 characters")
        String address
) {
    public String name() {
        return name;
    }

    public String address() {
        return address;
    }
}
