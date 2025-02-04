package com.smclinic.booking.repository;

import com.smclinic.booking.model.Booking;
import com.smclinic.booking.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RoomDAO {

    private final EntityManager entityManager;

    public RoomDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Room> findAvailableRooms() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> roomRoot = cq.from(Room.class);

        LocalDateTime now = LocalDateTime.now();
        ParameterExpression<LocalDateTime> nowParam = cb.parameter(LocalDateTime.class);

        Subquery<Booking> subquery = cq.subquery(Booking.class);
        Root<Booking> bookingRoot = subquery.from(Booking.class);

        subquery.select(bookingRoot)
                .where(
                        cb.equal(bookingRoot.get("room"), roomRoot),
                        cb.lessThanOrEqualTo(bookingRoot.get("startTime"), nowParam),
                        cb.greaterThanOrEqualTo(bookingRoot.get("endTime"), nowParam)
                );

        cq.select(roomRoot)
                .where(cb.not(cb.exists(subquery)));

        TypedQuery<Room> query = entityManager.createQuery(cq);
        query.setParameter(nowParam, now);

        return query.getResultList();
    }
}
