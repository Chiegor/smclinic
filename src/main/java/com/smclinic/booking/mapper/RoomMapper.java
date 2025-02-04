package com.smclinic.booking.mapper;

import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "coworkingSpaceId", source = "space.id")
    @Mapping(target = "coworkingSpaceName", source = "space.name")
    RoomDto toDto(Room room);

    List<RoomDto> toDtoList(List<Room> rooms);

    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "coworkingSpaceId", source = "room.space.id")
    @Mapping(target = "coworkingSpaceName", source = "room.space.name")
    @Mapping(target = "bookings", expression = "java(mapBookings(room.getBookings()))")
    RoomDto toDtoWithAvailability(Room room, boolean available);

    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    RoomDto.BookingSlotDto toBookingDTO(Booking booking);

    List<RoomDto.BookingSlotDto> mapBookings(List<Booking> bookings);
}
