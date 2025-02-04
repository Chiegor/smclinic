package com.smclinic.booking.model.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public record RoomSearchFilter(@Min(value = 1, message = "Минимальное количество мест - 1")
                               Integer minSeats,
                               @NotNull(message = "Начальная дата брони обязательна")
                               @FutureOrPresent(message = "Время старта брони должно быть в будущем")
                               LocalDateTime startTime,

                               @NotNull(message = "Дата окончания аренды обязательна")
                               @Future(message = "Время окончания аренды должно быть в будущем")
                               LocalDateTime endTime,

                               String space) {
    @AssertTrue(message = "Время окончания аренды должно быть позже чем дата старта")
    public boolean isEndTimeAfterStart() {
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "Интервал должен быть 30 минут")
    public boolean isValidTimeInterval() {
        return Duration.between(startTime, endTime).toMinutes() >= 30;
    }
}
