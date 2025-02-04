package com.smclinic.booking.controller;

import com.smclinic.booking.model.dto.BookingDto;
import com.smclinic.booking.model.dto.RoomDto;
import com.smclinic.booking.model.dto.RoomSearchFilter;
import com.smclinic.booking.model.dto.request.CreateBookingRequest;
import com.smclinic.booking.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rooms")
@Validated
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("available")
    public ResponseEntity<List<RoomDto>> findAvailableRooms(@Valid RoomSearchFilter filter) {
        return ResponseEntity.ok(roomService.findAvailableRooms(filter));
    }

    @Operation(summary = "Создать новое бронирование")
    @ApiResponse(responseCode = "201", description = "Бронирование создано")
    @PostMapping("create")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        BookingDto booking = roomService.createBooking(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }
}
