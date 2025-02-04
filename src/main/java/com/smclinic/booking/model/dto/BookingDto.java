package com.smclinic.booking.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingDto(
        UUID id,
        UUID roomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime createdAt) {
}
