package com.smclinic.booking.mapper;

import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.dto.BookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    BookingDto toDto(Booking booking);

}
