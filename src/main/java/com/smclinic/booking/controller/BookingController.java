package com.smclinic.booking.controller;

import com.smclinic.booking.model.dto.BookingDto;
import com.smclinic.booking.model.dto.request.CreateBookingRequest;
import com.smclinic.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("booking")
@Validated
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @ApiResponse(responseCode = "201", description = "Бронирование создано")
    @PostMapping("create")

    @Operation(
            summary = "Создать бронирование",
            requestBody = @RequestBody(
                    description = "Данные для бронирования",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Пример 1: Стандартное бронирование",
                                            value = """
                        {
                          "roomId": "550e8400-e29b-41d4-a716-446655440000",
                          "startTime": "2025-10-04T10:00:00",
                          "endTime": "2025-10-04T12:00:00"
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Пример 2: Минимальное бронирование",
                                            value = """
                        {
                          "roomId": "3e1afeb4-44d3-11ee-be56-0242ac120002",
                          "startTime": "2025-10-04T14:00:00",
                          "endTime": "2025-10-04T14:30:00"
                        }
                        """
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        BookingDto booking = bookingService.createBooking(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }
}
