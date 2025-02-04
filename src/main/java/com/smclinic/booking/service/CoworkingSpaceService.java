package com.smclinic.booking.service;

import com.smclinic.booking.exception.SpaceHasRoomsException;
import com.smclinic.booking.exception.SpaceNotFoundException;
import com.smclinic.booking.mapper.CoworkingSpaceMapper;
import com.smclinic.booking.model.CoworkingSpace;
import com.smclinic.booking.model.dto.CoworkingSpaceDto;
import com.smclinic.booking.model.dto.request.CreateSpaceRequest;
import com.smclinic.booking.model.dto.request.UpdateCoworkingSpaceRequest;
import com.smclinic.booking.repository.CoworkingSpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CoworkingSpaceService {

    private final CoworkingSpaceRepository spaceRepository;
    private final CoworkingSpaceMapper spaceMapper;

    public CoworkingSpaceService(CoworkingSpaceRepository spaceRepository, CoworkingSpaceMapper spaceMapper) {
        this.spaceRepository = spaceRepository;
        this.spaceMapper = spaceMapper;
    }

    @Transactional(readOnly = true)
    public List<CoworkingSpaceDto> getAllSpaces() {
        return spaceRepository.findAll().stream()
                .map(spaceMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CoworkingSpaceDto> getAllSpaces(String search) {
        if (search == null || search.isBlank()) {
            return spaceRepository.findAll().stream()
                    .map(spaceMapper::toDto)
                    .toList();
        }
        return spaceRepository.findByNameContainingOrAddressContaining(search)
                .stream()
                .map(spaceMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CoworkingSpaceDto getSpaceById(UUID id) {
        return spaceRepository.findById(id)
                .map(spaceMapper::toDto)
                .orElseThrow(() -> new SpaceNotFoundException(id));
    }

    @Transactional
    public CoworkingSpaceDto createSpace(CreateSpaceRequest request) {
        CoworkingSpace space = new CoworkingSpace();
        space.setName(request.name());
        space.setAddress(request.address());
        return spaceMapper.toDto(spaceRepository.save(space));
    }

    @Transactional
    public CoworkingSpaceDto updateSpace(UUID id, UpdateCoworkingSpaceRequest request) {
        CoworkingSpace space = spaceRepository.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException(id));

        spaceMapper.updateFromDto(request, space);
        space.setUpdatedAt(LocalDateTime.now());

        return spaceMapper.toDto(spaceRepository.save(space));
    }

    @Transactional
    public void deleteSpace(UUID id) {
        CoworkingSpace space = spaceRepository.findByIdWithRooms(id)
                .orElseThrow(() -> new SpaceNotFoundException(id));
        if (!space.getRooms().isEmpty()) {
            throw new SpaceHasRoomsException(id);
        }
        spaceRepository.delete(space);
    }
}
