package com.smclinic.booking.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingDto(
        UUID id,
        UUID roomId,
        String formattedStart,
        String formattedEnd,
        LocalDateTime createdAt) {
}
