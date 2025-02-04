package com.smclinic.booking.mapper;

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

    @Mapping(target = "coworkingSpaceId", source = "space.id")
    @Mapping(target = "coworkingSpaceName", source = "space.name")
    RoomDto toDto(Room room);

    List<RoomDto> toDtoList(List<Room> rooms);

    @Mapping(target = "isAvailable", source = "available")
    @Mapping(target = "coworkingSpaceId", source = "space.id")
    @Mapping(target = "coworkingSpaceName", source = "space.name")
    @Mapping(target = "bookings", expression = "java(mapBookings(room.getBookings()))")
    RoomDto toDtoWithAvailability(Room room, boolean available);
}
