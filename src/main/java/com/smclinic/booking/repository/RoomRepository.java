package com.smclinic.booking.repository;

import com.smclinic.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    List<Room> findBySpaceId(UUID spaceId);

    @Query("SELECT r FROM Room r WHERE " +
            "r.seats >= :minSeats AND " +
            "NOT EXISTS (" +
            "   SELECT b FROM Booking b WHERE " +
            "   b.room = r AND " +
            "   (b.startTime < :endTime AND b.endTime > :startTime)" +
            ")")
    List<Room> findAvailableRooms(
            @Param("minSeats") int minSeats,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
