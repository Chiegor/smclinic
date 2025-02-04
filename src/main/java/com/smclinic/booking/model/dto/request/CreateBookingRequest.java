package com.smclinic.booking.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateBookingRequest(
        @Schema(
                description = "ID комнаты",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        @NotNull UUID roomId,

        @Schema(
                description = "Время начала бронирования",
                example = "2025-10-04T10:00:00"
        )
        @NotNull @FutureOrPresent LocalDateTime startTime,

        @Schema(
                description = "Время окончания бронирования",
                example = "2025-10-04T12:00:00"
        )
        @NotNull @Future LocalDateTime endTime) {
}
