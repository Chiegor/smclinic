package com.smclinic.booking.controller;

import com.smclinic.booking.model.dto.RoomDto;
import com.smclinic.booking.model.dto.RoomSearchFilter;
import com.smclinic.booking.service.BookingService;
import com.smclinic.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<RoomDto>> findAvailableRooms() {
        return ResponseEntity.ok(roomService.findAllAvailableRooms());
    }

    @GetMapping("available_filter")
    public ResponseEntity<List<RoomDto>> findAvailableRoomsWithFilter(@Valid RoomSearchFilter filter) {
        return ResponseEntity.ok(roomService.findAvailableRoomsWithFilter(filter));
    }
}
