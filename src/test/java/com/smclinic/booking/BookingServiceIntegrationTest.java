package com.smclinic.booking;

import com.smclinic.booking.exception.BookingConflictException;
import com.smclinic.booking.exception.InvalidTimeSlotException;
import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.CoworkingSpace;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.BookingDto;
import com.smclinic.booking.model.dto.request.CreateBookingRequest;
import com.smclinic.booking.repository.BookingRepository;
import com.smclinic.booking.repository.CoworkingSpaceRepository;
import com.smclinic.booking.repository.RoomRepository;
import com.smclinic.booking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class BookingServiceIntegrationTest {

    private final CoworkingSpaceRepository coworkingSpaceRepository;
    private final BookingService bookingService;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceIntegrationTest(CoworkingSpaceRepository coworkingSpaceRepository,
                                         BookingService bookingService,
                                         RoomRepository roomRepository,
                                         BookingRepository bookingRepository) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
        this.bookingService = bookingService;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    CoworkingSpace space;
    Room room1;
    Room room2;
    Room room3;
    Room room4;

    @BeforeEach
    void setup() {
        space = new CoworkingSpace();
        space.setId(UUID.fromString("e82ac0ef-a7e5-4f1d-b7a5-c142e242c6b9"));
        space.setAddress("Test Address");
        space.setName("Test Space");
        space.setVersion(1L);

        room1 = new Room();
        room1.setId(UUID.randomUUID());
        room1.setRoomName("ROOM_1");
        room1.setSeats(6);
        room1.setSpace(space);

        room2 = new Room();
        room2.setId(UUID.randomUUID());
        room2.setRoomName("ROOM_2");
        room2.setSeats(20);
        room2.setSpace(space);

        room3 = new Room();
        room3.setId(UUID.randomUUID());
        room3.setRoomName("ROOM_3");
        room3.setSeats(12);
        room3.setSpace(space);

        room4 = new Room();
        room4.setId(UUID.randomUUID());
        room4.setRoomName("ROOM_4");
        room4.setSeats(4);
        room4.setSpace(space);


        space.setRooms(List.of(room1, room2, room3, room4));
        coworkingSpaceRepository.save(space);

        Booking existingBooking = new Booking(
                room1,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)
        );
        bookingRepository.save(existingBooking);
    }

    @Test
    void createBooking_shouldSuccessForValidTimeSlot() {
        Room room = roomRepository.save(room1);
        LocalDateTime start = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime end = start.plusMinutes(30);

        CreateBookingRequest request = new CreateBookingRequest(
                room.getId(),
                start,
                end
        );

        BookingDto result = bookingService.createBooking(request);

        assertThat(result).isNotNull();
        assertThat(result.startTime()).isEqualTo(start);
        assertThat(result.endTime()).isEqualTo(end);
    }

    @Test
    void createBooking_shouldFailForInvalidTimeSlot() {
        Room room = roomRepository.save(room2);
        LocalDateTime start = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS).plusMinutes(15);
        LocalDateTime end = start.plusMinutes(30);

        CreateBookingRequest request = new CreateBookingRequest(
                room.getId(),
                start,
                end
        );
        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(InvalidTimeSlotException.class)
                .hasMessageContaining("Ошибка интервала бронирования");
    }

    @Test
    void createBooking_shouldFailForOverlappingBookings() {
        Room room = roomRepository.save(room3);
        LocalDateTime start = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime end = start.plusHours(1);

        // Первое бронирование
        bookingService.createBooking(new CreateBookingRequest(room.getId(), start, end));

        // Конфликтующее бронирование
        CreateBookingRequest conflictingRequest = new CreateBookingRequest(
                room.getId(),
                start.plusMinutes(30),
                end.plusMinutes(30)
        );

        assertThatThrownBy(() -> bookingService.createBooking(conflictingRequest))
                .isInstanceOf(BookingConflictException.class)
                .hasMessageContaining("Комната уже забронирована!");
    }

    @Test
    void createBooking_shouldAllowAdjacentTimeSlots() {
        Room room = roomRepository.save(room4);
        LocalDateTime firstSlotStart = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime firstSlotEnd = firstSlotStart.plusMinutes(30);

        // Первое бронирование
        bookingService.createBooking(new CreateBookingRequest(
                room.getId(),
                firstSlotStart,
                firstSlotEnd
        ));

        // Следующий слот без перекрытия
        CreateBookingRequest nextSlotRequest = new CreateBookingRequest(
                room.getId(),
                firstSlotEnd,
                firstSlotEnd.plusMinutes(30)
        );

        assertThatCode(() -> bookingService.createBooking(nextSlotRequest))
                .doesNotThrowAnyException();
    }
}
