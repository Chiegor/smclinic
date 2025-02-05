package com.smclinic.booking.model.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;

import java.time.Duration;
import java.time.LocalDateTime;

public record RoomSearchFilter(
        @Min(1) Integer minSeats,
        @FutureOrPresent LocalDateTime startTime,
        @Future LocalDateTime endTime,
        String space
) {
    @AssertTrue
    public boolean isEndTimeAfterStart() {
        return endTime == null || startTime == null || endTime.isAfter(startTime);
    }

    @AssertTrue
    public boolean isValidTimeInterval() {
        return endTime == null || startTime == null
                || Duration.between(startTime, endTime).toMinutes() >= 30;
    }
}
