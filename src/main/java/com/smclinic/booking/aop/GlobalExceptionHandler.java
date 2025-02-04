package com.smclinic.booking.aop;

import com.smclinic.booking.exception.InvalidTimeSlotException;
import com.smclinic.booking.model.dto.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation error");
        problem.setProperty("violations", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                )));
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(InvalidTimeSlotException.class)
    public ResponseEntity<ApiError> handleInvalidTimeSlotException(InvalidTimeSlotException ex) {
        ApiError error = new ApiError(ex.getMessage(), "INVALID_TIME_SLOT");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
