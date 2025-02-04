package com.smclinic.booking.service;

import com.smclinic.booking.common.TimeSlotValidator;
import com.smclinic.booking.exception.BookingConflictException;
import com.smclinic.booking.exception.RoomNotFoundException;
import com.smclinic.booking.mapper.BookingMapper;
import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.BookingDto;
import com.smclinic.booking.model.dto.request.CreateBookingRequest;
import com.smclinic.booking.repository.BookingRepository;
import com.smclinic.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;
    private final TimeSlotValidator timeSlotValidator;
    private final RoomService roomService;

    @Transactional
    public BookingDto createBooking(CreateBookingRequest request) {
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new RoomNotFoundException(request.roomId()));

        timeSlotValidator.validateTimeSlot(request.startTime(), request.endTime());

        if (roomService.isRoomAvailable(room, request.startTime(), request.endTime())) {
            throw new BookingConflictException(request.roomId(), request.startTime(), request.endTime());
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setStartTime(request.startTime());
        booking.setEndTime(request.endTime());

        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
