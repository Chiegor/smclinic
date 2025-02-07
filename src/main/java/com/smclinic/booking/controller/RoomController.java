package com.smclinic.booking.controller;

import com.smclinic.booking.model.dto.RoomDto;
import com.smclinic.booking.model.dto.RoomSearchFilter;
import com.smclinic.booking.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    @GetMapping("/available_filter")
    public ResponseEntity<List<RoomDto>> findAvailableRooms(
            @RequestParam(required = false) Integer minSeats,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String space
    ) {
        RoomSearchFilter filter = new RoomSearchFilter(minSeats, startTime, endTime, space);
        return ResponseEntity.ok(roomService.findAvailableRoomsWithFilter(filter));
    }
}
