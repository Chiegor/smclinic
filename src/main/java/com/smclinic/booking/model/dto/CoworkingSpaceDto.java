package com.smclinic.booking.model.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CoworkingSpaceDto(UUID id,
                                String name,
                                String address,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                List<UUID> roomIds) {

    public record CreateRequest(
            @NotBlank String name,
            @NotBlank String address
    ) {
    }

    public record UpdateRequest(
            @NotBlank String name,
            @NotBlank String address
    ) {
    }
}
