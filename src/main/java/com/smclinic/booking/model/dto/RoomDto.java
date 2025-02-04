package com.smclinic.booking.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RoomDto(UUID id,
                      int seats,
                      UUID coworkingSpaceId,
                      String coworkingSpaceName,
                      boolean isAvailable,
                      List<BookingSlotDto> bookings) {

    public record BookingSlotDto(LocalDateTime startTime,
                                 LocalDateTime endTime) {
    }
}

