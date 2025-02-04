package com.smclinic.booking.exception;

import java.util.UUID;

public class SpaceNotFoundException extends RuntimeException {

    private final UUID spaceId;

    public SpaceNotFoundException(UUID space) {
        this.spaceId = space;
    }

    public Object getSpaceId() {
        return spaceId;
    }
}
