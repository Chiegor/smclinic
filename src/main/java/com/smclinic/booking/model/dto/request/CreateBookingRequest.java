package com.smclinic.booking.model.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateBookingRequest(
        @NotNull UUID roomId,
        @NotNull @FutureOrPresent LocalDateTime startTime,
        @NotNull @Future LocalDateTime endTime
) {

    @AssertTrue(message = "Время окончания аренды должно быть позже чем дата старта")
    public boolean isEndTimeValid() {
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "Интервал должен быть 30 минут")
    public boolean isTimeSlotValid() {
        return startTime.getMinute() % 30 == 0 && endTime.getMinute() % 30 == 0;
    }
}
