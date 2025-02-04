package com.smclinic.booking.controller;

import com.smclinic.booking.model.dto.CoworkingSpaceDto;
import com.smclinic.booking.model.dto.request.CreateSpaceRequest;
import com.smclinic.booking.model.dto.request.UpdateCoworkingSpaceRequest;
import com.smclinic.booking.service.CoworkingSpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("coworking")
@Validated
@AllArgsConstructor
public class CoworkingController {

    private final CoworkingSpaceService spaceService;

    @Operation(summary = "Создать новый коворкинг")
    @ApiResponse(responseCode = "201", description = "Коворкинг создан")
    @PostMapping(name = "create")
    public ResponseEntity<CoworkingSpaceDto> createSpace(@Valid @RequestBody CreateSpaceRequest request) {
        CoworkingSpaceDto createdSpace = spaceService.createSpace(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSpace);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoworkingSpaceDto> getSpaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(spaceService.getSpaceById(id));
    }

    @GetMapping(name = "all")
    public ResponseEntity<List<CoworkingSpaceDto>> getAllSpaces(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(spaceService.getAllSpaces(search));
        }
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoworkingSpaceDto> updateSpace(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCoworkingSpaceRequest request
    ) {
        return ResponseEntity.ok(spaceService.updateSpace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID id) {
        spaceService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }
}
