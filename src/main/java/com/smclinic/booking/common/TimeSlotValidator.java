package com.smclinic.booking.common;

import com.smclinic.booking.exception.InvalidTimeSlotException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeSlotValidator {

    public void validateTimeSlot(LocalDateTime start, LocalDateTime end) {
        validateTimeAlignment(start);
        validateTimeAlignment(end);
        validateDuration(start, end);
    }

    private void validateTimeAlignment(LocalDateTime time) {
        if (time.getMinute() % 30 != 0 || time.getSecond() != 0 || time.getNano() != 0) {
            throw new InvalidTimeSlotException("Время должно быть согласовано с 30-минутными интервалами.");
        }
    }

    private void validateDuration(LocalDateTime start, LocalDateTime end) {
        if (Duration.between(start, end).toMinutes() % 30 != 0) {
            throw new InvalidTimeSlotException("Продолжительность должна быть кратна 30 минутам.");
        }

        if (Duration.between(start, end).toMinutes() < 30) {
            throw new InvalidTimeSlotException("Минимальная продолжительность бронирования — 30 минут.");
        }
    }
}
