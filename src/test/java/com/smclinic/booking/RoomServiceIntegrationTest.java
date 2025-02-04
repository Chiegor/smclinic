package com.smclinic.booking;

import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.CoworkingSpace;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.RoomDto;
import com.smclinic.booking.model.dto.RoomSearchFilter;
import com.smclinic.booking.repository.BookingRepository;
import com.smclinic.booking.repository.CoworkingSpaceRepository;
import com.smclinic.booking.repository.RoomRepository;
import com.smclinic.booking.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RoomServiceIntegrationTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CoworkingSpaceRepository coworkingSpaceRepository;

    @BeforeEach
    void setup() {
        CoworkingSpace space = new CoworkingSpace();
        space.setId(UUID.fromString("e82ac0ef-a7e5-4f1d-b7a5-c142e242c6b9"));
        space.setAddress("Test Address");
        space.setName("Test Space");
        space.setVersion(1L);

        Room room1 = new Room();
        room1.setId(UUID.randomUUID());
        room1.setRoomName("ROOM_1");
        room1.setSeats(6);
        room1.setSpace(space);

        Room room2 = new Room();
        room2.setId(UUID.randomUUID());
        room2.setRoomName("ROOM_2");
        room2.setSeats(20);
        room2.setSpace(space);

//        roomRepository.saveAll(List.of(room1, room2));
        space.setRooms(List.of(room1, room2));

        coworkingSpaceRepository.save(space);

        // Существующее бронирование для room1
        Booking existingBooking = new Booking(
                room1,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)
        );
        bookingRepository.save(existingBooking);
    }

    @Test
    void findAvailableRooms_shouldReturnOnlyAvailableRooms() {
        RoomSearchFilter filter = new RoomSearchFilter(
                4,
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(4),
                "Test Space"
        );
        List<RoomDto> result = roomService.findAvailableRooms(filter);
        assertThat(result).hasSize(2);
    }

    @Test
    void findAvailableRooms_shouldExcludeBookedRooms() {
        RoomSearchFilter filter = new RoomSearchFilter(
                4,
                LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS),
                LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.HOURS),
                "Test Space"
        );
        List<RoomDto> result = roomService.findAvailableRooms(filter);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).seats()).isEqualTo(6);
    }

    @Test
    void findAvailableRooms_shouldFilterByMinSeats() {
        RoomSearchFilter filter = new RoomSearchFilter(
                5,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                "Test Space"
        );
        List<RoomDto> result = roomService.findAvailableRooms(filter);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).seats()).isEqualTo(6);
    }
}
