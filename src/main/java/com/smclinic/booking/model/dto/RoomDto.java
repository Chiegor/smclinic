package com.smclinic.booking.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RoomDto(UUID id,
                      int seats,
                      UUID coworkingSpaceId,
                      String coworkingSpaceName,
                      Boolean isAvailable,
                      List<BookingSlotDto> bookings) {

    public RoomDto {
        if (isAvailable == null) {
            isAvailable = true; // todo Дефолтное значение, надо в будущем исправить логику
        }
    }

    public record BookingSlotDto(LocalDateTime startTime,
                                 LocalDateTime endTime) {
    }
}

