package com.smclinic.booking.repository;

import com.smclinic.booking.model.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoworkingSpaceRepository extends JpaRepository<CoworkingSpace, UUID> {

    @Query("SELECT DISTINCT cs FROM CoworkingSpace cs LEFT JOIN FETCH cs.rooms WHERE cs.id = :id")
    Optional<CoworkingSpace> findByIdWithRooms(@Param("id") UUID id);

    @Query("SELECT s FROM CoworkingSpace s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<CoworkingSpace> findByNameContainingOrAddressContaining(String search);
}
