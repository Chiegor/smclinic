package com.smclinic.booking.model.dto.error;

import java.time.LocalDateTime;

public record ApiError(String message, String errorCode, LocalDateTime timestamp) {
    public ApiError(String message, String errorCode) {
        this(message, errorCode, LocalDateTime.now());
    }
}
