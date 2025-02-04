package com.smclinic.booking.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BookingConflictException extends RuntimeException {

    private final UUID roomId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public BookingConflictException(UUID roomId, LocalDateTime startTime, LocalDateTime endTime) {
        super(String.format("Комната %s уже забронирована между %s и %s",
                roomId, startTime, endTime));
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
