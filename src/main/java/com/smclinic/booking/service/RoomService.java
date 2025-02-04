package com.smclinic.booking.service;

import com.smclinic.booking.exception.RoomHasBookingsException;
import com.smclinic.booking.exception.RoomNotFoundException;
import com.smclinic.booking.exception.SpaceNotFoundException;
import com.smclinic.booking.mapper.RoomMapper;
import com.smclinic.booking.model.CoworkingSpace;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.BookingDto;
import com.smclinic.booking.model.dto.RoomDto;
import com.smclinic.booking.model.dto.RoomSearchFilter;
import com.smclinic.booking.model.dto.request.CreateBookingRequest;
import com.smclinic.booking.model.dto.request.CreateRoomRequest;
import com.smclinic.booking.model.dto.request.UpdateRoomRequest;
import com.smclinic.booking.repository.BookingRepository;
import com.smclinic.booking.repository.CoworkingSpaceRepository;
import com.smclinic.booking.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CoworkingSpaceRepository spaceRepository;
    private final RoomMapper roomMapper;
    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<RoomDto> findAvailableRooms(RoomSearchFilter filter) {
        List<Room> rooms = roomRepository.findAvailableRooms(
                filter.minSeats(),
                filter.startTime(),
                filter.endTime()
        );
        return rooms.stream()
                .map(room -> {
                    boolean isAvailable = isRoomAvailable(room, filter.startTime(), filter.endTime());
                    return roomMapper.toDtoWithAvailability(room, isAvailable);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsBySpace(UUID spaceId) {
        return roomRepository.findBySpaceId(spaceId).stream()
                .map(roomMapper::toDto)
                .toList();
    }

    public boolean isRoomAvailable(Room room, LocalDateTime start, LocalDateTime end) {
        return !bookingRepository.existsByRoomAndTimeRange(
                room.getId(),
                start,
                end
        );
    }

    @Transactional
    public RoomDto createRoom(UUID spaceId, CreateRoomRequest request) {
        CoworkingSpace space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceNotFoundException(spaceId));

        Room room = new Room();
        room.setSpace(space);
        room.setSeats(request.seats());

        return roomMapper.toDto(roomRepository.save(room));
    }

    @Transactional
    public RoomDto updateRoom(UUID roomId, UpdateRoomRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        room.setSeats(request.seats());
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Transactional
    public void deleteRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        if (!room.getBookings().isEmpty()) {
            throw new RoomHasBookingsException(roomId);
        }

        roomRepository.delete(room);
    }
}
