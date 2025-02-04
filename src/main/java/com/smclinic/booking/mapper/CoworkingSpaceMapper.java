package com.smclinic.booking.mapper;

import com.smclinic.booking.model.CoworkingSpace;
import com.smclinic.booking.model.Room;
import com.smclinic.booking.model.dto.CoworkingSpaceDto;
import com.smclinic.booking.model.dto.request.UpdateCoworkingSpaceRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CoworkingSpaceMapper {
    @Mapping(target = "roomIds", source = "rooms")
    CoworkingSpaceDto toDto(CoworkingSpace entity);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CoworkingSpace toEntity(CoworkingSpaceDto.CreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default void updateFromDto(UpdateCoworkingSpaceRequest dto, @MappingTarget CoworkingSpace entity) {
        if (dto.name() != null) {
            entity.setName(dto.name());
        }
        if (dto.address() != null) {
            entity.setAddress(dto.address());
        }
    }

    default List<UUID> map(List<Room> rooms) {
        return rooms.stream()
                .map(Room::getId)
                .collect(Collectors.toList());
    }
}
